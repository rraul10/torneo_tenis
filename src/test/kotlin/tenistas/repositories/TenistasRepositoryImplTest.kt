package tenistas.repositories

import database.DatabaseConnection
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import tenistas.models.Tenista
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class TenistasRepositoryImplTest {
    private val connection = DatabaseConnection()
    private lateinit var repository: TenistasRepositoryImpl
    val Nadal = Tenista(
        UUID.fromString("004c5d50-30a3-4416-a9c4-209b63d8f78c"),
        "Rafael Nadal",
        "Argentina",
        185,
        75,
        2650,
        "Derecha",
        LocalDate.of(1985, 10, 25),
        LocalDateTime.now(),
        LocalDateTime.now()
    )

    @BeforeEach
    fun setUp() {
        repository.createTable()
        repository.saveTenista(Nadal)
    }

    @Test
    fun deleteById() {

        val result = repository.deleteById(Nadal.id)

        assertAll(
            { assert(result?.nombre ==Nadal.nombre)},
            { assert(result?.id == Nadal.id)}
        )
    }

    fun deleteNotFound() {
        val id = UUID.fromString("004c5d50-30a3-4416-a9c4-209b63d8f78v")

        val result = repository.deleteById(id)

        assert(result == null)
    }
}