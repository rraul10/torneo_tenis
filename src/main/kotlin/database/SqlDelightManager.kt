package database

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import config.Config
import dev.javierhvicente.database.AppDatabase
import org.lighthousegames.logging.logging

private val logger = logging()


class SqlDelightManager(
    private val config: Config
) {
    val databaseQueries: DatabaseQueries by lazy { initQueries() }

    init {
        logger.debug { "Inicializando la base de datos con SqlDelight" }
        if (config.removeDatabase) {
            clearData()
        }
        initialize()
    }

    fun initQueries(): DatabaseQueries {
        val driver = if (Config.databaseInMemory) {
            logger.debug { "SqlDeLightClient - InMemory" }
            JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        } else {
            logger.debug { "SqlDeLightClient - File: ${Config.dataBaseUrl}" }
            JdbcSqliteDriver(Config.dataBaseUrl)
        }

        AppDatabase.Schema.create(driver)
        val database = AppDatabase(driver)

        return database.databaseQueries
    }


    fun clearData() {
        logger.debug { "Borrando datos de la base de datos" }
        databaseQueries.transaction {
            databaseQueries.deleteAllTenistas()
        }
    }

    private fun initialize() {
        logger.debug { "SqlDeLightClient.initialize()" }
        if (Config.dataBaseInit) {
            databaseQueries.transaction {
            }
        }
    }
}