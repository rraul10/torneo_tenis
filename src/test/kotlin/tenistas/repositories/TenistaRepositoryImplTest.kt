package tenistas.repositories

import database.DatabaseConnection
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
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

    @Test
    fun saveTenista() {
        val tenista=Tenista(
            id = UUID.fromString("fb00de71-22ed-40ff-92d4-ad8aba256446"),
            nombre = "TestNombre",
            pais="TestPais",
            altura= 180,
            peso= 70,
            puntos= 10,
            mano= "ZURDO",
            fecha_nacimiento = LocalDate.of(2024,1,1)
        )

        val result= repository.saveTenista(tenista)

        Assertions.assertAll(
            { Assertions.assertEquals(result.id, tenista.id) },
            { Assertions.assertEquals(result.nombre, tenista.nombre) },
            { Assertions.assertEquals(result.pais, tenista.pais) },
            { Assertions.assertEquals(result.altura, tenista.altura) },
            { Assertions.assertEquals(result.peso, tenista.peso) },
            { Assertions.assertEquals(result.puntos, tenista.puntos) },
            { Assertions.assertEquals(result.mano, tenista.mano) },
            { Assertions.assertEquals(result.fecha_nacimiento, tenista.fecha_nacimiento) }
        )
    }

    @Test
    fun getTenistaByName() {
        //arrange
        //act
        repository.saveTenista(Nadal)
        val tenista = repository.getTenistaByName("Rafael Nadal")
        //assert
        assertNotNull(tenista)
        assertEquals(Nadal, tenista)
    }

    @Test
    fun getTenistaByNameNotFound() {
        //arrange
        //act
        repository.saveTenista(Nadal)
        val tenista = repository.getTenistaByName("Rafael Nad")
        //assert
        assertNull(tenista)
        assertNotEquals(Nadal, tenista)
    }

    @Test
    fun deleteById (){
        val result = repository.deleteById(Nadal.id)

        assertAll(
            { assert(result?.nombre == Nadal.nombre)},
            { assert(result?.id == Nadal.id)}
        )
    }

    fun deleteNotFound() {
        val id = UUID.fromString("004c5d50-30a3-4416-a9c4-209b63d8f78V")

        // Act
        val result = repository.deleteById(id)

        // Assert
        assert(result == null)
    }

}