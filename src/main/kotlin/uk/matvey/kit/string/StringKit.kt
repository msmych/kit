package uk.matvey.kit.string

import java.time.LocalDate
import java.util.Locale
import java.util.UUID

object StringKit {

    fun capital(str: String): String {
        return str.replaceFirstChar {
            if (it.isLowerCase()) {
                it.titlecase(Locale.ENGLISH)
            } else {
                it.toString()
            }
        }
    }

    fun String.toUuid() = UUID.fromString(this)

    fun String.toLocalDate() = LocalDate.parse(this)
}