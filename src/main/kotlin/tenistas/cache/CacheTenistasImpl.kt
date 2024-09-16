package tenistas.cache

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import config.Config.cacheSize
import tenistas.errors.CacheError
import tenistas.mapper.logger
import tenistas.models.Tenista

class CacheTenistasImpl(
    private val size: Int
): CacheTenistas<Long, Tenista> {
    private val cache = mutableMapOf<Long, Tenista>()
    override fun get(key: Long): Result<Tenista, CacheError> {
        logger.debug { "Obteniendo valor de la cache" }
        return if (cache.containsKey(key)){
            Ok(cache.getValue(key))
        }else{
            Err(CacheError.CacheErrorValid("No existe el valor en la cache"))
        }
    }


    override fun put(key: Long, value: Tenista): Result<Tenista, CacheError> {
        logger.debug { "Salvando valor en la cache" }
        if (cache.size > cacheSize && !cache.containsKey(key)){
            logger.debug { "Eliminando primer valor de la cache" }
            cache.remove(cache.keys.first())
        }
        cache[key] = value
        return Ok(value)
    }

    override fun remove(key: Long): Result<Tenista, CacheError> {
        logger.debug { "Eliminando valor de la cache" }
        return if (cache.containsKey(key)){
            Ok(cache.remove(key)!!)
        }else{
            Err(CacheError.CacheErrorValid("No existe el valor en la cache"))
        }
    }

    override fun clear() {
        logger.debug { "Limpiando cache" }
        cache.clear()
    }
}