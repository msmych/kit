package uk.matvey.telek

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * https://core.telegram.org/bots/api#inlinekeyboardmarkup
 */
@Serializable
data class TgInlineKeyboardMarkup(
    @SerialName("inline_keyboard")
    val inlineKeyboard: List<List<TgInlineKeyboardButton>>,
)
