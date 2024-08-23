package uk.matvey.telek

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.timeout
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
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
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonObject
import uk.matvey.kit.json.JsonKit.JSON
import uk.matvey.kit.json.JsonKit.asBool
import uk.matvey.kit.json.JsonKit.bool
import uk.matvey.kit.json.JsonKit.jsonArraySerialize

class TgBot(
    token: String,
    private val longPollingSeconds: Int = 0,
    private val onUpdatesRetrievalException: (Exception) -> Unit = {},
    private val onUpdateProcessingException: (Exception) -> Unit = {},
) {
    private val baseUrl = "https://api.telegram.org/bot$token"

    private val client = HttpClient(CIO) {
        install(HttpTimeout)
        install(ContentNegotiation) {
            json(JSON)
        }
        install(DefaultRequest) {
            contentType(Application.Json)
        }
    }

    suspend fun getUpdates(offset: Int? = null): List<TgUpdate> {
        return try {
            client.get("$baseUrl/getUpdates") {
                timeout {
                    requestTimeoutMillis = (1000L * longPollingSeconds).coerceAtLeast(5000)
                }
                offset?.let { url.parameters.append("offset", it.toString()) }
                longPollingSeconds.takeIf { it > 0 }?.let { url.parameters.append("timeout", it.toString()) }
            }
        } catch (e: ConnectTimeoutException) {
            return listOf()
        } catch (e: HttpRequestTimeoutException) {
            return listOf()
        } catch (e: Exception) {
            onUpdatesRetrievalException(e)
            return listOf()
        }
            .tgResult()
            .jsonArray
            .map(JSON::decodeFromJsonElement)
    }

    suspend fun start(block: suspend (TgUpdate) -> Unit) = coroutineScope {
        while (isActive) {
            getUpdates()
                .onEach { update ->
                    try {
                        block(update)
                    } catch (e: Exception) {
                        onUpdateProcessingException(e)
                    }
                }
                .lastOrNull()
                ?.let { lastUpdate ->
                    getUpdates(lastUpdate.id + 1)
                }
                ?: delay(100)
        }
    }

    suspend fun sendMessage(
        chatId: Long,
        text: String,
        parseMode: TgParseMode? = null,
        inlineKeyboard: List<List<TgInlineKeyboardButton>>? = null,
    ): TgMessage {
        return client.post("$baseUrl/sendMessage") {
            setBody(
                buildJsonObject {
                    put("chat_id", chatId)
                    put("text", text)
                    parseMode?.let { put("parse_mode", it.name) }
                    inlineKeyboard?.let { ikb ->
                        putJsonObject("reply_markup") {
                            put("inline_keyboard", jsonArraySerialize(ikb))
                        }
                    }
                }
            )
        }
            .tgResult()
            .let(JSON::decodeFromJsonElement)
    }

    suspend fun updateMessageInlineKeyboard(
        message: TgMessage,
        inlineKeyboard: List<List<TgInlineKeyboardButton>>,
    ): TgMessage {
        return client.post("$baseUrl/editMessageText") {
            setBody(
                buildJsonObject {
                    put("chat_id", message.chat.id)
                    put("text", message.text())
                    put("message_id", message.id)
                    message.parseMode?.let { put("parse_mode", it.name) }
                    putJsonObject("reply_markup") {
                        put("inline_keyboard", jsonArraySerialize(inlineKeyboard))
                    }
                }
            )
        }
            .tgResult()
            .let(JSON::decodeFromJsonElement)
    }

    suspend fun answerCallbackQuery(callbackQueryId: String): Boolean {
        return client.post("$baseUrl/answerCallbackQuery") {
            setBody(
                buildJsonObject {
                    put("callback_query_id", callbackQueryId)
                }
            )
        }
            .tgResult()
            .asBool()
    }

    private suspend fun HttpResponse.tgResult(): JsonElement {
        return body<JsonObject>()
            .also {
                if (!it.bool("ok")) {
                    throw TgRequestException.fromTgResponse(it)
                }
            }
            .getValue("result")
    }
}
