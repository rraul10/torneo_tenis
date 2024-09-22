package tenistas.repositories

import org.lighthousegames.logging.logging
import tenistas.mapper.logger
import tenistas.mapper.toTenista
import tenistas.models.Tenista
import java.sql.Connection
import java.sql.SQLException
import java.time.LocalDate
import java.time.LocalDateTime

private val looger = logging()

/**
 * Repositorio de tenistas que se comunica con la base de datosç
 * @param dbManager: SqlDelightManager
 * @author Javier Hernández
 * @since 1.0
 */
class TenistasRepositoryImpl(
    private val connection: Connection
): TenistasRepository {
    fun createTable(){
        val sql = """
            CREATE TABLE IF NOT EXISTS tenistas(
                id TEXT PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                pais TEXT NOT NULL,
                altura INTEGER NOT NULL,
                peso INTEGER NOT NULL,
                puntos INTEGER NOT NULL,
                mano TEXT NOT NULL,
                fecha_nacimiento TEXT NOT NULL,
                created_at TEXT NOT NULL,
                upadated_at TEXT NOT NULL
            );
        """.trimIndent()
        connection.createStatement().execute(sql)
    }

    /**
     * Obtiene a todos los tenistas de la base de datos
     * @return List<T enista>
     * @author Javier Hernández
     * @since 1.0
     */
    override fun getAllTenistas(): List<Tenista> {
        looger.debug { "Obteniendo a todos los Tenistas" }
        val sql = "SELECT * FROM tenistas"
        val tenistas = mutableListOf<Tenista>()
        try{
            connection.createStatement().use { statement ->
                statement.executeQuery(sql).use { resultSet ->
                    while (resultSet.next()) {
                        val tenista = Tenista(
                            resultSet.getLong("id"),
                            resultSet.getString("nombre"),
                            resultSet.getString("pais"),
                            resultSet.getInt("altura"),
                            resultSet.getInt("peso"),
                            resultSet.getInt("puntos"),
                            resultSet.getString("manos"),
                            fecha_nacimiento = LocalDate.parse(resultSet.getString("fecha_nacimiento")),
                            createdAt = LocalDateTime.parse(resultSet.getString("created_at")),
                            updatedAt = LocalDateTime.parse(resultSet.getString("updated_at")),
                        )
                        tenistas.add(tenista)
                    }
                }
            }
        }catch (e: SQLException){
            looger.error { "Error al obtener los Tenistas: ${e.message}" }
            e.printStackTrace()
        }
        return tenistas
    }

    /**
     * Obtiene a un tenista por su id
     * @param id: Long
     * @return Tenista? - Tenista encontrado o null si no existe
     * @author Samuel Cortés
     * @since 1.0
     */

    override fun getTenistaById(id: Long): Tenista? {
        looger.debug { "Obteniendo el Tenista con id: $id" }
        val sql = "SELECT * FROM tenistas WHERE id = $id"
        var tenista : Tenista? = null
        try {
            connection.prepareStatement(sql).use { statement ->
                statement.setInt(1,id.toInt())
                val resultSet = statement.executeQuery()
                if (resultSet.next()){
                    tenista = Tenista(
                        resultSet.getLong("id"),
                        resultSet.getString("nombre"),
                        resultSet.getString("pais"),
                        resultSet.getInt("altura"),
                        resultSet.getInt("peso"),
                        resultSet.getInt("puntos"),
                        resultSet.getString("manos"),
                        fecha_nacimiento = LocalDate.parse(resultSet.getString("fecha_nacimiento")),
                        createdAt = LocalDateTime.parse(resultSet.getString("created_at")),
                        updatedAt = LocalDateTime.parse(resultSet.getString("updated_at")),
                    )
                }
            }
        }catch (e: SQLException){
            logger.error { "Error al obtener el Tenista: ${e.message}" }
        }
        return tenista
    }

    /**
     * Obtiene a un tenista por su nombre
     * @param nombre: String
     * @return Tenista? - Tenista encontrado o null si no existe
     * @author Alvaro Herrero
     * @since 1.0
     */
    override fun getTenistaByName(nombre: String): Tenista? {
        looger.debug { "Obteniendo el Tenista con nombre: $nombre" }
        val sql = "SELECT * FROM tenistas WHERE nombre =?"
        var tenista: Tenista? = null
        try {
            connection.createStatement().use { statement ->
                statement.executeQuery(sql).use { resultSet ->
                    if (resultSet.next()) {
                        tenista = Tenista(
                            resultSet.getLong("id"),
                            resultSet.getString("nombre"),
                            resultSet.getString("pais"),
                            resultSet.getInt("altura"),
                            resultSet.getInt("peso"),
                            resultSet.getInt("puntos"),
                            resultSet.getString("manos"),
                            fecha_nacimiento = LocalDate.parse(resultSet.getString("fecha_nacimiento")),
                            createdAt = LocalDateTime.parse(resultSet.getString("created_at")),
                            updatedAt = LocalDateTime.parse(resultSet.getString("updated_at")),
                        )
                    }
                }
            }
        } catch (e: SQLException) {
            looger.error { "Error al obtener el Tenista por nombre: ${e.message}" }
            e.printStackTrace()
        }
        return tenista
    }

    /**
     * Guarda un nuevo tenista en la base de datos
     * @param tenista: Tenista
     * @return Tenista - Tenista guardado en la base de datos con su id asignado
     * @author Yahya El Hadri
     * @since 1.0
     */
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

    /**
     * Actualiza un tenista en la base de datos
     * @param tenista: Tenista
     * @return Tenista? - Tenista actualizado o null si no existe el tenista con el id proporcionado
     * @since 1.0
     * @author Javier Ruiz
     */
    override fun updateTenista(tenista: Tenista): Tenista? {
        looger.debug { "Actualizando el Tenista con id: ${tenista.id}" }
        val sql = "UPDATE tenistas SET nombre=?, pais=?, altura=?, peso=?, puntos=?, mano=?,fecha_nacimiento=?,updated_at=? WHERE id=? "
        try {
            connection.createStatement().use { statement ->
                statement.executeQuery(sql).use { resultSet ->
                    resultSet.getLong("id")
                    resultSet.getString("nombre")
                    resultSet.getString("pais")
                    resultSet.getInt("altura")
                    resultSet.getInt("peso")
                    resultSet.getInt("puntos")
                    resultSet.getString("manos")
                    tenista.fecha_nacimiento = LocalDate.parse(resultSet.getString("fecha_nacimiento"))
                    tenista.createdAt = LocalDateTime.parse(resultSet.getString("created_at"))
                    tenista.updatedAt = LocalDateTime.parse(resultSet.getString("updated_at"))

                    }
                }
        }catch (e: SQLException){
            looger.error { "Error al obtener los Tenistas: ${e.message}" }
            e.printStackTrace()
        }
        return tenista
    }

    /**
     * Elimina un tenista de la base de datos
     * @param id: Long
     * @return Tenista? - Tenista eliminado o null si no existe el tenista con el id proporcionado
     * @since 1.0
     * @author Javier Hernández
     */
    override fun deleteById(id: Long): Tenista? {
        looger.debug { "Eliminando el Tenista con id: $id" }
        val result = this.getTenistaById(id) ?: return null
        db.deleteById(id)
        return result
    }

}