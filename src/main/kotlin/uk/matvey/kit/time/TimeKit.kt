package uk.matvey.kit.time

import java.time.Clock
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Year
import java.time.YearMonth

object TimeKit {

    val CLOCK = Clock.systemUTC()

    fun clock() = CLOCK

    fun instant() = CLOCK.instant()

    fun localDate() = LocalDate.now(clock())

    fun localTime() = LocalDateTime.now(clock())

    fun localDateTime() = LocalDateTime.now(clock())

    fun yearMonth() = YearMonth.now(clock())

    fun year() = Year.now(clock())
}
