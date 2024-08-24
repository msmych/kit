package uk.matvey.telek

import kotlinx.serialization.Serializable

@Serializable
data class TgCallbackQuery(
    val id: String,
    val from: TgUser,
    val message: TgMessage,
    val data: String? = null,
) {

    fun data() = requireNotNull(data)
}
