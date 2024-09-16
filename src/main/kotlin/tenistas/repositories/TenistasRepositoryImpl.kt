package tenistas.repositories

import database.SqlDelightManager
import org.lighthousegames.logging.logging
import tenistas.mapper.logger
import tenistas.mapper.toTenista
import tenistas.models.Tenista

private val looger = logging()
class TenistasRepositoryImpl(
    private val dbManager: SqlDelightManager
): TenistasRepository {
    private val db = dbManager.databaseQueries
    override fun getAllTenistas(): List<Tenista> {
        looger.debug { "Obteniendo a todos los Tenistas" }
        return db.selectAll().executeAsList().map { it.toTenista() }
    }

    override fun getTenistaById(id: Long): Tenista? {
        looger.debug { "Obteniendo el Tenista con id: $id" }
        return db.selectById(id).executeAsOneOrNull()?.toTenista()
    }

    override fun getTenistaByName(nombre: String): Tenista? {
        looger.debug { "Obteniendo el Tenista con nombre: $nombre" }
        return db.selectByName(nombre).executeAsOneOrNull()?.toTenista()
    }

    override fun saveTenista(tenista: Tenista): Tenista {
        looger.debug { "Creando un nuevo Tenista con nombre: ${tenista.nombre}" }
         db.transaction{
            db.insertTenista(
                nombre = tenista.nombre,
                pais = tenista.pais,
                altura = tenista.altura.toLong(),
                peso = tenista.peso.toLong(),
                puntos = tenista.puntos.toLong(),
                mano = tenista.mano,
                fecha_nacimiento = tenista.fecha_nacimiento.toString(),
                created_at = tenista.createdAt.toString(),
                upadated_at = tenista.updatedAt.toString(),
            )
        }
        return tenista
    }

    override fun updateTenista(tenista: Tenista): Tenista? {
        looger.debug { "Actualizando el Tenista con id: ${tenista.id}" }
        var result = this.getTenistaById(tenista.id)?: return null
        db.updateTenista(
            id = tenista.id,
            nombre = tenista.nombre,
            pais = tenista.pais,
            altura = tenista.altura.toLong(),
            peso = tenista.peso.toLong(),
            puntos = tenista.puntos.toLong(),
            mano = tenista.mano,
            fecha_nacimiento = tenista.fecha_nacimiento.toString(),
            upadated_at = tenista.updatedAt.toString(),
        )
        logger.debug { "Tenista actualizado correctamente: ${tenista.nombre}" }
        result = this.getTenistaById(tenista.id)!!
        return result
    }

    override fun deleteById(id: Long): Tenista? {
        looger.debug { "Eliminando el Tenista con id: $id" }
        val result = this.getTenistaById(id) ?: return null
        db.deleteById(id)
        return result
    }

}