package database

import config.Config
import org.lighthousegames.logging.logging
import java.sql.Connection
import java.sql.DriverManager

private val logger = logging()

object DataBaseManager : AutoCloseable {
    var connection: Connection? = null
        private set

    init {
        initConexion()
        if (Config.databaseInitTables) {
            initTables()
        }
        if (Config.databaseInitData) {
            initData()
        }
    }

    private fun initData() {
        logger.debug { "Iniciando la carga de datos" }
        try {
            val data = ClassLoader.getSystemResourceAsStream("data.sql")?.bufferedReader()!!
            scriptRunner(data, true)
            logger.debug { "Datos cargados" }
        } catch (e: Exception) {
            logger.error { "Error al cargar los datos: ${e.message}" }
        }
    }

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

    private fun initConexion() {
        logger.debug {"Iniciando conexión con la base de datos"}
        if (connection == null || connection!!.isClosed) {
            connection = DriverManager.getConnection(Config.databaseUrl)
        }
        logger.debug { "Conexión con la base de datos inicializada" }
    }



    override fun close() {
        TODO("Not yet implemented")
    }

}