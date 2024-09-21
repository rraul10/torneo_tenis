package database

import config.Config
import org.apache.ibatis.jdbc.ScriptRunner
import org.lighthousegames.logging.logging
import java.io.PrintWriter
import java.io.Reader
import java.sql.Connection
import java.sql.DriverManager

private val logger = logging()

object DataBaseManager : AutoCloseable {
    var connection: Connection? = null
        private set

    /**
     * Inicializamos la base de datos
     */

    init {
        initConexion()
        if (Config.databaseInitTables) {
            initTables()
        }
    }

    /**
     * Inicializamos las tablas de la base de datos.
     */

    private fun initTables() {
        logger.debug { "Iniciando la creación de tablas" }
        try {
            val tablas = ClassLoader.getSystemResourceAsStream("schema.sql")?.bufferedReader()!!
            scriptRunner(tablas, true)
            logger.debug { "Tablas creadas" }
        } catch (e: Exception) {
            logger.error { "Error al crear las tablas: ${e.message}" }
        }
    }

    /**
     * Inicializamos la conexión con la base de datos.
     */

    private fun initConexion() {
        logger.debug {"Iniciando conexión con la base de datos"}
        if (connection == null || connection!!.isClosed) {
            connection = DriverManager.getConnection(Config.dataBaseUrl)
        }
        logger.debug { "Conexión con la base de datos inicializada" }
    }

    /**
     * Cerramos la conexión con la base de datos.
     */

    override fun close() {
        logger.debug {"Cerrando conexión con la base de datos"}
        if (!connection!!.isClosed) {
            connection!!.close()
        }
        logger.debug {"Conexion con la base de datos cerrada"}
    }

    /**
     * Función para usar la base de datos y cerrarla al finalizar la operación.
     */

    fun <T> use (block: (DataBaseManager) -> T) {
        try {
            initConexion()
            block(this)
        } catch (e: Exception) {
            logger.error {"Error en la base de datos: ${e.message}"}
        } finally {
            close()
        }
    }

    /**
     * Función para ejecutar un script SQL en la base de datos.
     */

    private fun scriptRunner(reader: Reader, logWriter: Boolean = false) {
        logger.debug {"Ejecutando script SQL con log: $logWriter"}
        val sr = ScriptRunner(connection)
        sr.setLogWriter(if (logWriter) PrintWriter(System.out) else null)
        sr.runScript(reader)
    }

}