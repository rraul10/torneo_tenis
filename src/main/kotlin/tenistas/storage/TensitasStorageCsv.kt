package tenistas.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.lighthousegames.logging.logging
import tenistas.dto.TenistaDto
import tenistas.errors.FileError
import tenistas.mapper.toTenista
import tenistas.models.Tenista
import java.io.File

private val logger = logging()
class TensitasStorageCsv: TenistasStorage {
     fun load(file: File): Result<List<Tenista>, FileError> {
        logger.debug { "Loading tenistas from file: $file" }
        return try {
            val lines = file.reader().readLines()
            val hasHeader: Boolean
            if (lines.firstOrNull()?.startsWith("id,nombre,pais") == true) {
                hasHeader = true
            } else {
                hasHeader = false
            }

            val dataLines = if (hasHeader) {
                lines.drop(1)
            } else {
                lines
            }

            Ok(dataLines
                .map {
                    val data = it.split(",")
                    TenistaDto(
                        id = data[0],
                        nombre = data[1],
                        pais = data[2],
                        altura = data[3],
                        peso = data[4],
                        puntos = data[5],
                        mano = data[6],
                        fecha_nacimiento = data[7],
                        created_at = data[8],
                        updated_at = data[9]
                    ).toTenista()
                }
            )
        } catch (e: Exception) {
            logger.error(e) { "Error loading tenistas from file: $file" }
            Err(FileError.FileReadingError("Error loading tenistas from file: $file"))
        }
    }
    override fun store(file: File, tenistas: List<Tenista>): Result<Unit, FileError>{
        logger.debug { "Storing Tenistas into $file" }
        return try {
            file.appendText("id,nombre,pais,altura,peso,puntos,mano,fecha_nacimiento\n")
            tenistas.forEach {
                file.appendText("${it.id},${it.nombre},${it.pais},${it.altura},${it.peso},${it.puntos},${it.mano},${it.fecha_nacimiento}\n")
            }
            Ok(Unit)
        }catch (e: Exception) {
            logger.error(e) { "Error storing tenistas into file: $file" }
            Err(FileError.FileWritingError("Error storing tenistas into file: $file"))
        }

    }
}

