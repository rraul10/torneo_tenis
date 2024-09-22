package tenistas.repositories

import database.DatabaseConnection
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertAll
import tenistas.models.Tenista
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TenistaRepositoryImplTest {
    private val connection = DatabaseConnection()
    private lateinit var repository: TenistasRepositoryImpl
    val Nadal = Tenista(UUID.fromString("004c5d50-30a3-4416-a9c4-209b63d8f78c"),"Rafael Nadal", "Argentina", 185, 75, 2650, "Derecha", LocalDate.of(1985, 10, 25), LocalDateTime.now(), LocalDateTime.now())
    @BeforeEach
    fun setUp(){
        repository.createTable()
        repository.saveTenista(Nadal)
    }

    @Test
    fun getAllTenistas() {
        val result = repository.getAllTenistas()
        assertAll(
            {assert(result.size == 1)},
            {assert(result[0].nombre == "Rafael Nadal")},
            { assertEquals(UUID.fromString("004c5d50-30a3-4416-a9c4-209b63d8f78c"), result[0].id) }
        )
    }

}