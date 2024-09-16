package tenistas.service

import com.github.michaelbull.result.*
import org.lighthousegames.logging.logging
import tenistas.cache.CacheTenistasImpl
import tenistas.errors.FileError
import tenistas.errors.TenistaError
import tenistas.models.Tenista
import tenistas.repositories.TenistasRepository
import tenistas.storage.TenistasStorage
import tenistas.storage.TensitasStorageImpl
import java.io.File
private val logger = logging()
class TenistasServiceImpl(
    private val tenistasStorage: TenistasStorage,
    private val tenistasRepository: TenistasRepository,
    private val cache: CacheTenistasImpl
) : TenistasService {
    override fun getAllTenistas(): Result<List<Tenista>, TenistaError> {
        logger.debug { "Getting all tenistas" }
        return Ok(tenistasRepository.getAllTenistas())
    }

    override fun getTenistaById(id: Long): Result<Tenista, TenistaError> {
        logger.debug { "Getting tenista by id: $id" }
        return cache.get(id).mapBoth(
            success = {
                logger.debug { "Tenista from cache: $it" }
                Ok(it)
            },
            failure = {
                logger.debug { "Estudiante no encontrado en la cache" }
                tenistasRepository.getTenistaById(id)
                    ?.let { Ok(it) }
                    ?: Err(TenistaError.TenistaNotFound("Tenista no encontrado con id: $id"))
            }
        )
    }

    override fun getTenistaByNombre(nombre: String): Result<Tenista, TenistaError> {
        logger.debug { "Getting tenista by nombre: $nombre" }
        return tenistasRepository.getTenistaByName(nombre)
            ?.let { Ok(it) }
            ?: Err(TenistaError.TenistaNotFound("Tenista no encontrado con nombre: $nombre"))
    }

    override fun createTenista(tenista: Tenista): Result<Tenista, TenistaError> {
        logger.debug { "Creating tenista: $tenista" }
        return Ok(tenistasRepository.saveTenista(tenista)).also { cache.put(tenista.id, tenista) }
    }

    override fun updateTenista(tenista: Tenista): Result<Tenista, TenistaError> {
        logger.debug { "Updating tenista: $tenista" }
        return tenistasRepository.updateTenista(tenista)
            .also { cache.put(tenista.id, tenista) }
            ?.let { Ok(it) }
            ?: Err(TenistaError.TenistaNotUpdated("No se encontró el tenista con id: ${tenista.id}"))
    }

    override fun deleteTenistaById(id: Long): Result<Unit, TenistaError> {
        logger.debug { "Deleting tenista by id: $id" }
        return tenistasRepository.deleteById(id)
            ?.let {
                cache.remove(id)
                Ok(Unit)
            }
            ?: Err(TenistaError.TenistaNotDeleted("No se puedo eliminar al tenista con id: $id"))

    }

    override fun readCSV(file: File): Result<List<Tenista>, FileError> {
        logger.debug { "Reading CSV file: $file" }
        return tenistasStorage.readCsv(file).andThen {tenistas ->
            tenistas.forEach { p ->
                tenistasRepository.saveTenista(p)
                logger.debug { "Stored tenista: $p" }
            }
            Ok(tenistas)
        }
    }

    override fun writeCSV(file: File, tenistas: List<Tenista>): Result<Unit, FileError> {
        logger.debug { "Writing CSV file: $file" }
        return tenistasStorage.storeCsv(file, tenistas)
    }

    override fun writeJson(file: File, tenistas: List<Tenista>): Result<Unit, FileError> {
        logger.debug { "Writing JSON file: $file" }
        return tenistasStorage.storeJson(file, tenistas)
    }

    override fun writeXml(file: File, tenistas: List<Tenista>): Result<Unit, FileError> {
        logger.debug { "Writing XML file: $file" }
        return tenistasStorage.storeXml(file, tenistas)
    }

}