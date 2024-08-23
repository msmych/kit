package uk.matvey.telek

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TgUpdate(
    @SerialName("update_id")
    val id: Int,
    val message: TgMessage? = null,
    @SerialName("callback_query")
    val callbackQuery: TgCallbackQuery? = null,
) {

    fun message() = requireNotNull(message)

    fun callbackQuery() = requireNotNull(callbackQuery)
}
