package tenistas.validators

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import tenistas.errors.CsvErrors
import java.io.File
import java.time.LocalDate

private val logger = org.lighthousegames.logging.logging()

/**
 * Valida el formato del archivo CSV.
 * @param csvContent Contenido del archivo CSV.
 * @return Result<Unit, CsvErrors> con el resultado de la validación.
 * @since 1.0
 * @author Javier Hernández
 */
fun validateCsvFormat(csvContent: String): Result<Unit, CsvErrors> {
    val archivo = File(csvContent)
    val lineas = archivo.readLines()
    val numeroFilas = lineas.size
    logger.debug { "Validando el formato del CSV" }
    if(numeroFilas !in 100..101) {
        logger.error { "Numero de filas incorrecto"}
        return Err(CsvErrors.InvalidCsvFormat("El archivo debe tener entre 100 y 100 filas. Tiene ${numeroFilas} filas."))
    }
    for ((indice, linea) in lineas.withIndex()) {
        val columnas = linea.split(',')
        if(columnas.size!= 8) {
            logger.error { "Numero de columnas incorrecto"}
            return Err(CsvErrors.InvalidCsvFormat("La fila $indice debe tener 8 columnas. Tiene ${columnas.size} columnas."))
        }
    }
    return Ok(Unit)
}

fun validateCsvEntries(item: List<String>): Result<List<String>, CsvErrors> {

    val regexNombres = Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ'-]+(\\s+[a-zA-ZáéíóúÁÉÍÓÚñÑ'-]+)*\$")
    val regexMano = Regex("^(DIESTRO|ZURDO)\$")
    val regexFechaISO = Regex("^\\d{4}-\\d{2}-\\d{2}\$")

    return when {
        item[0].isEmpty() -> Err(CsvErrors.InvalidTenistaFormat("El ID no puede ser un campo vacío"))
        item[0].isBlank() -> Err(CsvErrors.InvalidTenistaFormat("El ID no puede ser un campo vacío"))
        item[0].toIntOrNull() == null -> Err(CsvErrors.InvalidTenistaFormat("El ID debe ser un número entero: ${item[0]}"))
        item[0].toInt() < 0 -> Err(CsvErrors.InvalidTenistaFormat("El ID no puede ser un campo negativo: ${item[0]}"))

        item[1].isEmpty() -> Err(CsvErrors.InvalidTenistaFormat("El nombre no puede ser un campo vacío"))
        item[1].isBlank() -> Err(CsvErrors.InvalidTenistaFormat("El nombre no puede ser un campo vacío"))
        !item[1].matches(regexNombres) -> Err(CsvErrors.InvalidTenistaFormat("El nombre solo puede contener letras: ${item[1]}"))

        item[2].isEmpty() -> Err(CsvErrors.InvalidTenistaFormat("El pais no puede ser un campo vacío"))
        item[2].isBlank() -> Err(CsvErrors.InvalidTenistaFormat("El pais no puede ser un campo vacío"))
        !item[2].matches(regexNombres) -> Err(CsvErrors.InvalidTenistaFormat("El pais solo puede contener letras: ${item[2]}"))

        item[3].toIntOrNull() == null -> Err(CsvErrors.InvalidTenistaFormat("La altura debe ser un número entero (en cm): ${item[3]}"))
        item[3].toInt() <= 0 -> Err(CsvErrors.InvalidTenistaFormat("La altura no puede ser un campo negativo: ${item[3]}"))

        item[4].toIntOrNull() == null -> Err(CsvErrors.InvalidTenistaFormat("El peso debe ser un número entero (en kg): ${item[4]}"))
        item[4].toInt() <= 0 -> Err(CsvErrors.InvalidTenistaFormat("El peso no puede ser un campo negativo: ${item[4]}"))

        item[5].toIntOrNull() == null -> Err(CsvErrors.InvalidTenistaFormat("Los puntos deben ser un número entero: ${item[5]}"))
        item[5].toInt() < 0 -> Err(CsvErrors.InvalidTenistaFormat("Los puntos no pueden ser un campo negativo: ${item[5]}"))

        item[6].isEmpty() -> Err(CsvErrors.InvalidTenistaFormat("La mano no puede ser un campo vacío"))
        item[6].isBlank() -> Err(CsvErrors.InvalidTenistaFormat("La mano no puede ser un campo vacío"))
        !item[6].uppercase().matches(regexMano) -> Err(CsvErrors.InvalidTenistaFormat("La mano solo puede ser 'DIESTRO' o 'ZURDO': ${item[6]}"))

        item[7].isEmpty() -> Err(CsvErrors.InvalidTenistaFormat("La fecha de nacimiento no puede ser un campo vacío"))
        item[7].isBlank() -> Err(CsvErrors.InvalidTenistaFormat("La fecha de nacimiento no puede ser un campo vacío"))
        !item[7].matches(regexFechaISO) -> Err(CsvErrors.InvalidTenistaFormat("La fecha de nacimiento debe tener el formato 'AAAA-MM-DD': ${item[7]}"))
        item[7] > LocalDate.now().toString() -> Err(CsvErrors.InvalidTenistaFormat("La fecha de nacimiento no puede ser superior a la fecha actual: ${item[7]}"))

        else -> Ok(item)
    }
}

