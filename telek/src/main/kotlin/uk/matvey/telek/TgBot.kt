package uk.matvey.telek

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.timeout
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType.Application
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.put
import uk.matvey.kit.json.JsonKit.JSON
import uk.matvey.kit.json.JsonKit.bool
import uk.matvey.kit.json.JsonKit.jsonObjectDeserialize

class TgBot(
    token: String,
    private val longPollingSeconds: Int = 0,
) {
    private val baseUrl = "https://api.telegram.org/bot$token"

    private val client = HttpClient(CIO) {
        install(HttpTimeout)
        install(ContentNegotiation) {
            json(JSON)
        }
    }

    suspend fun getUpdates(offset: Int? = null): List<TgUpdate> {
        val rs = try {
            client.get("$baseUrl/getUpdates") {
                timeout {
                    requestTimeoutMillis = (1000L * longPollingSeconds).coerceAtLeast(5000)
                }
                offset?.let { url.parameters.append("offset", it.toString()) }
                longPollingSeconds.takeIf { it > 0 }?.let { url.parameters.append("timeout", it.toString()) }
            }
        } catch (e: HttpRequestTimeoutException) {
            return listOf()
        }
        return rs.tgResult().jsonArray.map(JSON::decodeFromJsonElement)
    }

    suspend fun start(block: suspend (TgUpdate) -> Unit) = coroutineScope {
        while (isActive) {
            getUpdates()
                .onEach { update -> block(update) }
                .lastOrNull()
                ?.let { lastUpdate ->
                    getUpdates(lastUpdate.id + 1)
                }
                ?: delay(100)
        }
    }

    suspend fun sendMessage(chatId: Long, text: String): JsonObject {
        val rs = client.post("$baseUrl/sendMessage") {
            contentType(Application.Json)
            setBody(
                buildJsonObject {
                    put("chat_id", chatId)
                    put("text", text)
                }
            )
        }
        return rs.tgResult().jsonObject
    }

    private suspend fun HttpResponse.tgResult(): JsonElement {
        return jsonObjectDeserialize(bodyAsText())
            .also {
                if (!it.bool("ok")) {
                    throw TgRequestException.fromTgResponse(it)
                }
            }
            .getValue("result")
    }
}