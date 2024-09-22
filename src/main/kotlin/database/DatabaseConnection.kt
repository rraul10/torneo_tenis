package database

import config.Config
import org.lighthousegames.logging.logging
import tenistas.mapper.logger
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
private val looger = logging()
class DatabaseConnection {
    private val databaseUrl = Config.dataBaseUrl

    fun connect(): Connection?{
        return try{
            logger.debug{"Connecting to the database"}
            DriverManager.getConnection(databaseUrl)
        }catch (e: SQLException){
            logger.error{"Error connecting to the database: ${e.message}"}
            e.printStackTrace()
            null
        }
    }
}