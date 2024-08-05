package uk.matvey.kit.random

import kotlin.random.Random

object RandomKit {

    val LOWERCASE_LETTERS = ('a'..'z').toList()
    val UPPERCASE_LETTERS = ('A'..'Z').toList()
    val LETTERS = LOWERCASE_LETTERS + UPPERCASE_LETTERS
    val DIGITS = ('0'..'9').toList()
    val ALPHANUMERIC = LETTERS + DIGITS
    val SPECIAL_SYMBOLS = "!@#$%^&*()_+-=[]{}|;:,.<>?".toList()
    val ALL_SYMBOLS = ALPHANUMERIC + SPECIAL_SYMBOLS + ' '

    val RANDOM = Random.Default

    fun randomInt() = RANDOM.nextInt()

    fun randomInt(until: Int) = RANDOM.nextInt(until)

    fun randomInt(range: IntRange) = RANDOM.nextInt(range.first, range.last())

    fun randomStr(
        length: Int = randomInt(until = 32),
        chars: List<Char> = ALL_SYMBOLS,
    ): String {
        require(length >= 0) { "Length must be non-negative" }
        return buildString {
            repeat(length) {
                append(chars[RANDOM.nextInt(chars.size)])
            }
        }
    }

    fun randomAlphabetic(length: Int = randomInt(1..<32)): String {
        return randomStr(length, LETTERS)
    }

    fun randomNumeric(length: Int = randomInt(1..<32)): String {
        return randomStr(length, DIGITS)
    }

    fun randomAlphanumeric(length: Int = randomInt(1..<32)): String {
        return randomStr(length, ALPHANUMERIC)
    }

    fun randomHttps(): String {
        val domains = listOf(
            "au",
            "ca",
            "co",
            "com",
            "de",
            "edu",
            "es",
            "fr",
            "gov",
            "io",
            "it",
            "net",
            "nl",
            "org",
            "ru",
            "uk",
        )
        val name = (0..randomInt(1..<3))
            .joinToString(".") {
                randomAlphanumeric(randomInt(1..12))
            }
        return "https://$name.${domains.random()}"
    }
}