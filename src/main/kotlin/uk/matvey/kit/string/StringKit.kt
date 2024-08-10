package uk.matvey.kit.string

import java.util.Locale

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
}