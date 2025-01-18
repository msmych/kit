package uk.matvey.kit.random

import uk.matvey.kit.string.StringKit.ALL_SYMBOLS
import uk.matvey.kit.string.StringKit.ALPHANUMERIC
import uk.matvey.kit.string.StringKit.DIGITS
import uk.matvey.kit.string.StringKit.LETTERS
import uk.matvey.kit.string.StringKit.capitalize
import kotlin.random.Random

object RandomKit {

    fun randomStr(
        length: Int = Random.nextInt(DEFAULT_RANDOM_STRING_LENGTH),
        chars: List<Char> = ALL_SYMBOLS,
    ): String {
        require(length >= 0) { "Length must be non-negative" }
        return buildString {
            repeat(length) {
                append(chars[Random.nextInt(chars.size)])
            }
        }
    }

    fun randomAlphabetic(length: Int = DEFAULT_RANDOM_STRING_LENGTH): String {
        return randomStr(length, LETTERS)
    }

    fun randomNumeric(length: Int = DEFAULT_RANDOM_STRING_LENGTH): String {
        return randomStr(length, DIGITS)
    }

    fun randomAlphanumeric(length: Int = DEFAULT_RANDOM_STRING_LENGTH): String {
        return randomStr(length, ALPHANUMERIC)
    }

    fun randomName(): String {
        return capitalize(randomAlphabetic(Random.nextInt(2, 13)).lowercase())
    }

    fun randomFullName(): String {
        return "${randomName()} ${randomName()}"
    }

    private const val DEFAULT_RANDOM_STRING_LENGTH = 12
}