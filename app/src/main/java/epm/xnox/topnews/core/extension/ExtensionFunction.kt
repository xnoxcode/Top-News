package epm.xnox.topnews.core.extension

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun String.formatDate(): String {
    return try {
        val zonedDateTime = ZonedDateTime.parse(this)
        val formatter =
            DateTimeFormatter.ofPattern("dd MMM. yyyy", Locale("es", "ES"))
        zonedDateTime.format(formatter)
    } catch (e: Exception) {
        "Sin fecha"
    }
}