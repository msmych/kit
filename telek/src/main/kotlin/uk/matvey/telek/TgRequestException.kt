package uk.matvey.telek

import kotlinx.serialization.json.JsonObject
import uk.matvey.kit.json.JsonKit.str

class TgRequestException(message: String): RuntimeException(message) {

    companion object {
        fun fromTgResponse(rs: JsonObject): TgRequestException {
            return TgRequestException(rs.str("description"))
        }
    }
}