import com.github.michaelbull.result.Err
import com.github.michaelbull.result.mapBoth
import tenistas.errors.ArgsErrors
import tenistas.errors.CsvErrors
import tenistas.validators.validateArgs
import tenistas.validators.validateCsvFormat

fun main(args: Array<String>) {
    if(args.isEmpty()) {
        println("No arguments provided.")
    }
    validateArgs(args[0]).mapBoth(
        success = { println("Archivo válido: $it") },
        failure = {
            Err(ArgsErrors.InvalidArgumentsError("Error: El argumento introducido no es válido"))
        }
    )
    validateCsvFormat(args[0]).mapBoth(
        success = { println("Formato CSV válido: $it") },
        failure = {
            Err(CsvErrors.InvalidCsvFormat("Error: El formato del archivo no es CSV"))
        }
    )
}