package tenistas.service

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapBoth
import org.lighthousegames.logging.logging
import tenistas.cache.CacheTenistasImpl
import tenistas.errors.TenistaError
import tenistas.models.Tenista
import tenistas.repositories.TenistasRepository
import tenistas.storage.TenistasStorage
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
        TODO("Not yet implemented")
    }

    override fun createTenista(tenista: Tenista): Result<Tenista, TenistaError> {
        TODO("Not yet implemented")
    }

    override fun updateTenista(tenista: Tenista): Result<Tenista, TenistaError> {
        TODO("Not yet implemented")
    }

    override fun deleteTenistaById(id: Long): Result<Unit, TenistaError> {
        TODO("Not yet implemented")
    }

    override fun readCSV(file: File): Result<List<Tenista>, TenistaError> {
        TODO("Not yet implemented")
    }

    override fun writeCSV(file: File, tenistas: List<Tenista>): Result<Unit, TenistaError> {
        TODO("Not yet implemented")
    }

    override fun writeJson(file: File, tenistas: List<Tenista>): Result<Unit, TenistaError> {
        TODO("Not yet implemented")
    }

    override fun writeXml(file: File, tenistas: List<Tenista>): Result<Unit, TenistaError> {
        TODO("Not yet implemented")
    }

}