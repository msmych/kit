package uk.matvey.telek

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TgInlineKeyboardButton(
    val text: String,
    val url: String? = null,
    @SerialName("callback_data")
    val callbackData: String? = null,
) {

    companion object {

        fun url(text: String, url: String) = TgInlineKeyboardButton(text, url = url)

        fun callbackData(text: String, callbackData: String) = TgInlineKeyboardButton(text, callbackData = callbackData)
    }
}
