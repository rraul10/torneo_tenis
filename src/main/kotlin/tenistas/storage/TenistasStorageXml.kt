package tenistas.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import nl.adaptivity.xmlutil.serialization.XML
import org.lighthousegames.logging.logging
import tenistas.errors.FileError
import tenistas.mapper.toTenistaDto
import tenistas.models.Tenista
import java.io.File
private val logger = logging()
class TenistasStorageXml: TenistasStorage {
    override fun store(file: File, tenistas: List<Tenista>): Result<Unit, FileError> {
        logger.debug { "Storing tenistas into $file" }
        return try {
            val xml = XML {
                indent = 4 
            }
            val xmlString = xml.encodeToString(tenistas.map { it.toTenistaDto() })
            file.writeText(xmlString)
            Ok(Unit)
        }catch (e: Exception) {
            logger.error(e) { "Error storing tenistas into $file" }
            Err(FileError.FileWritingError("Error storing tenistas into $file"))
        }
    }
    }
}