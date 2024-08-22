package uk.matvey.telek

import kotlinx.serialization.Serializable

@Serializable
data class TgCallbackQuery(
    val id: String,
    val from: TgUser,
    val data: String? = null,
)
