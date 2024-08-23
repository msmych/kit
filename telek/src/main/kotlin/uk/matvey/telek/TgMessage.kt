package uk.matvey.telek

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TgMessage(
    @SerialName("message_id")
    val id: Int,
    val from: TgUser? = null,
    val text: String? = null,
    val chat: TgChat,
    @SerialName("parse_mode")
    val parseMode: TgParseMode? = null,
) {

    fun from() = requireNotNull(from)

    fun text() = requireNotNull(text)
}
