package tenistas.validators

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.lighthousegames.logging.logging
import tenistas.errors.ArgsErrors
import java.io.File

private val logger = logging()

fun validateArgs(path: String): Result<String, ArgsErrors> {
    logger.debug { "Comprobando si el archivo existe: $path" }
     if (!File(path).exists() && !File(path).canRead() && !File(path).isFile) {
        Err(ArgsErrors.FileDoesNotExistError("El archivo $path no existe o no se puede leer"))
    }
    val archivo = File(path)
    if (archivo.extension.equals("csv", ignoreCase = true)) {
        Err(ArgsErrors.InvalidExtension("El archivo $path no tiene extensión .csv"))
    }
    return Ok(path)
}
