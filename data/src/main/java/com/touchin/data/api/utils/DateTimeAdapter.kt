package com.touchin.data.api.utils

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

open class DateTimeAdapter {

    // FIXME договориться с бэком об одном формате дат
    private val parsers = listOf(
            DateTimeFormat.forPattern("yyyy-MM-dd"),
            DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"),
            DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss"),
            DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ"),
            DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"),
            DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ"),
            DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"),
            DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSZ"),
            DateTimeFormat.forPattern("dd.MM.yyyy")
    )

    protected open val toJsonDateTimeFormatter: DateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd")

    @ToJson
    open fun toJson(dateTime: DateTime?): String? = dateTime?.let { toJsonDateTimeFormatter.print(it) }

    @Suppress("TooGenericExceptionCaught")
    @FromJson
    open fun fromJson(dateTime: String?): DateTime? {
        if (dateTime == null) return null
        var date: DateTime? = null
        var minusDay = true

        //if we will try to parse one of these dates, we'll get exception: Cannot parse "1981-04-01":
        // Illegal instant due to time zone offset transition (Europe/Moscow)
        val dateTimeToParse = when (dateTime){
            "1981-04-01" -> "1981-04-02"
            "1982-04-01" -> "1982-04-02"
            "1983-04-01" -> "1983-04-02"
            "1984-04-01" -> "1984-04-02"
            else -> {
                minusDay = false
                dateTime
            }
        }

        for (parser in parsers) {
            try {
                date = parser.parseDateTime(dateTimeToParse)
                if (minusDay) date = date.minusDays(1)
                break
            } catch (e: Throwable) {
                // формат не подходт, продолжим перебор
                continue
            }
        }
        return date
    }
}
