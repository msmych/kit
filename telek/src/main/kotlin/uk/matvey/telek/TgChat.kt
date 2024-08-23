package uk.matvey.telek

import kotlinx.serialization.Serializable

@Serializable
data class TgChat(
    val id: Long,
    val type: Type,
) {

    enum class Type {
        private,
        group,
        channel,
    }
}
