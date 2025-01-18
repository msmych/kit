package uk.matvey.kit.string

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.util.Locale
import java.util.UUID

object StringKit {

    val LOWERCASE_LETTERS = ('a'..'z').toList()
    val UPPERCASE_LETTERS = ('A'..'Z').toList()
    val LETTERS = LOWERCASE_LETTERS + UPPERCASE_LETTERS
    val DIGITS = ('0'..'9').toList()
    val ALPHANUMERIC = LETTERS + DIGITS
    val SPECIAL_SYMBOLS = "!@#$%^&*()_+-=[]{}|;:,.<>?".toList()
    val ALL_SYMBOLS = ALPHANUMERIC + SPECIAL_SYMBOLS + ' '

    fun capitalize(str: String): String {
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

    fun String.toLocalDateTime() = LocalDateTime.parse(this)

    fun String.toYearMonth() = YearMonth.parse(this)

    fun String.toInstant() = Instant.parse(this)

    fun fullName(firstName: String?, lastName: String?): String? {
        return listOfNotNull(firstName, lastName)
            .takeIf { it.isNotEmpty() }
            ?.joinToString(" ")
    }
}