package uk.matvey.telek

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.timeout
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.delay
import kotlinx.serialization.json.decodeFromJsonElement
import uk.matvey.kit.json.JsonKit.JSON
import uk.matvey.kit.json.JsonKit.arr
import uk.matvey.kit.json.JsonKit.jsonObjectDeserialize

class TgBot(
    token: String,
    private val longPollingSeconds: Int = 0,
) {
    private val baseUrl = "https://api.telegram.org/bot$token"

    private val client = HttpClient(CIO) {
        install(HttpTimeout)
    }

    suspend fun getUpdates(offset: Int? = null): List<TgUpdate> {
        val rs = try {
            client.get("$baseUrl/getUpdates") {
                timeout {
                    requestTimeoutMillis = (1000L * longPollingSeconds).coerceAtLeast(5000)
                }
                offset?.let { url.parameters.append("offset", it.toString()) }
                longPollingSeconds.takeIf { it > 0 }.let { url.parameters.append("timeout", it.toString()) }
            }
        } catch (e: HttpRequestTimeoutException) {
            return listOf()
        }
        return jsonObjectDeserialize(rs.bodyAsText())
            .arr("result")
            .map(JSON::decodeFromJsonElement)
    }

    suspend fun start(block: suspend (TgUpdate) -> Unit) {
        while (true) {
            getUpdates()
                .onEach { update -> block(update) }
                .lastOrNull()
                ?.let { lastUpdate ->
                    getUpdates(lastUpdate.id + 1)
                }
                ?: delay(100)
        }
    }
}