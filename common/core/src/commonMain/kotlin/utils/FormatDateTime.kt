package utils

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

private val isoFormat = LocalDateTime.Format {
    date(LocalDate.Formats.ISO); char(' '); time(LocalTime.Formats.ISO)
}

/**
 * `18:43 - 09.11.2024`
 */
@OptIn(FormatStringsInDatetimeFormats::class)
private val format = LocalDateTime.Format {
    byUnicodePattern("HH:mm - dd.MM.yyyy")
}

fun Instant.toLocalDateTimeText() = toLocalDateTime(TimeZone.currentSystemDefault()).format(format)