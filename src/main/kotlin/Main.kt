import com.github.michaelbull.result.Err
import com.github.michaelbull.result.mapBoth
import tenistas.errors.ArgsErrors
import tenistas.validators.validateArgs

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
}