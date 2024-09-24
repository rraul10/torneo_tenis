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
    val nadal = Tenista(UUID.fromString("23d41191-8a78-4c02-9127-06e76b56af17") ,"Rafael nadal", "Argentina", 185, 75, 2650, "Derecha", LocalDate.of(1985, 10, 25), LocalDateTime.now(), LocalDateTime.now())
    @BeforeEach
    fun setUp(){
        repository = TenistasRepositoryImpl(connection)
        repository.saveTenista(nadal)
    }


    @Test
    fun getAllTenistas() {
        val result = repository.getAllTenistas()
        assertAll(
            {assert(result.size == 1)},
            {assert(result[0].nombre == nadal.nombre)},
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
    fun getTenistaById() {
        val result = repository.getTenistaById(nadal.id)
        assertAll(
            {assert(result?.nombre == nadal.nombre)},
            {assert(result?.id == nadal.id)}
        )
    }
    @Test
    fun getTenistaByIdNotFound() {
        val id = UUID.fromString("18b81a66-d11e-4659-88fa-bbac4d8f88dc")

        // Act
        val result = repository.getTenistaById(id)

        // Assert
        assert(result ==null)
        }

    @Test
    fun getTenistaByName() {
        //arrange
        //act
        val tenista = repository.getTenistaByName(nadal.nombre)
        //assert
        assertNotNull(tenista)
        assertEquals(nadal, tenista)
    }

    @Test
    fun getTenistaByNameNotFound() {
        //arrange
        //act
        repository.saveTenista(nadal)
        val tenista = repository.getTenistaByName("Ra")
        //assert
        assertNull(tenista)
        assertNotEquals(nadal, tenista)
    }

    @Test
    fun updateTenista() {
        val tenista = Tenista(
            id = nadal.id,
            nombre = "Rafael Nadal",
            pais = "España",
            altura = 185,
            peso = 85,
            puntos = 2650,
            mano = "Derecha",
            fecha_nacimiento = LocalDate.of(1985, 10, 25)
        )

        val result = repository.updateTenista(nadal.id, tenista)

        assertAll(
            { assert(result?.nombre == tenista.nombre) },
            { assert(result?.pais == tenista.pais) },
            { assert(result?.altura == tenista.altura) },
            { assert(result?.peso == tenista.peso) },
            { assert(result?.puntos == tenista.puntos) },
        )
    }

    @Test
    fun updateNotFoundTenista() {
        val id = UUID.fromString("18b81a66-d11e-4659-88fa-bbac4d8f88dc")
        val result = repository.updateTenista(id, nadal)
        assertNull(result)
    }

    @Test
    fun deleteById (){
        val result = repository.deleteById(nadal.id)

        assertAll(
            { assert(result?.nombre == nadal.nombre)},
            { assert(result?.id == nadal.id)}
        )
    }

    @Test
    fun deleteNotFound() {
        val id = UUID.fromString("18b81a66-d11e-4659-88fa-bbac4d8f88dc")

        // Act
        val result = repository.deleteById(id)

        // Assert
        assert(result==null)
        }

}