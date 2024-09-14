package Locale

import java.time.LocalDate
import java.time.LocalDateTime

fun String.returnDateString(): LocalDate {
    val cadena=this.split("-")
    return LocalDate.of(cadena[0].toInt(),cadena[1].toInt(),cadena[2].toInt())
}

fun String.retunrDateTimeString(): LocalDateTime{
    val cadena=this.split("-")
    return LocalDateTime.of(cadena[0].toInt(),cadena[1].toInt(),cadena[2].toInt(),cadena[3].toInt(),cadena[4].toInt())
}