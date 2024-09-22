package tenistas.repositories

import database.DatabaseConnection
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import tenistas.models.Tenista
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

class TenistasRepositoryImplTest {


    private val connection = DatabaseConnection()
    private lateinit var repository: TenistasRepositoryImpl
    @BeforeEach
    fun connect(){
        connection.connect()
        repository = connection.connect()?.let { TenistasRepositoryImpl(it) }!!
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

        assertAll(
            {assertEquals(result.id, tenista.id)},
            {assertEquals(result.nombre, tenista.nombre)},
            {assertEquals(result.pais, tenista.pais)},
            {assertEquals(result.altura, tenista.altura)},
            {assertEquals(result.peso, tenista.peso)},
            {assertEquals(result.puntos, tenista.puntos)},
            {assertEquals(result.mano, tenista.mano)},
            {assertEquals(result.fecha_nacimiento, tenista.fecha_nacimiento)}
        )


    }
}