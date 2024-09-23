package tenistas.service


import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.extension.ExtendWith
import tenistas.cache.CacheTenistasImpl
import tenistas.errors.FileError
import tenistas.errors.TenistaError
import tenistas.models.Tenista
import tenistas.repositories.TenistasRepositoryImpl
import tenistas.storage.TenistasStorageImpl
import java.io.File
import java.time.LocalDate
import java.util.*
import kotlin.test.assertTrue

@ExtendWith(MockKExtension::class)
class TenistasServiceImplTest {


    @MockK
    lateinit var repository: TenistasRepositoryImpl
    @MockK
    lateinit var cache: CacheTenistasImpl
    @MockK
    lateinit var storage: TenistasStorageImpl
    @InjectMockKs
    lateinit var services: TenistasServiceImpl

    private val tenista= Tenista(
        id = UUID.fromString("fb00de71-22ed-40ff-92d4-ad8aba256446"),
        nombre = "TestNombre",
        pais="TestPais",
        altura= 180,
        peso= 70,
        puntos= 10,
        mano= "ZURDO",
        fecha_nacimiento = LocalDate.of(2024,1,1)
    )

    @Test
    fun getAllTenistas() {

        val tenistas= listOf(tenista)

        every { repository.getAllTenistas() } returns tenistas

        val result=services.getAllTenistas().value

        assertAll(
            { assertEquals(1, result.size) },
            { assertEquals("TestNombre", result[0].nombre) }
        )

        verify(exactly = 1) { repository.getAllTenistas() }
    }

    @Test
    fun getTenistaByIdInCahe() {

        every { cache.get(tenista.id) } returns tenista

        val result = services.getTenistaById(tenista.id).value

        assertAll(
            { assertEquals("Test1", result.nombre) },
        )

        verify(exactly = 1) { cache.get(tenista.id) }
        verify(exactly = 0) { repository.getTenistaById(tenista.id) }

    }

    @Test
    fun getTenistaByIdInRepo() {

        every { cache.get(tenista.id) } returns null
        every { repository.getTenistaById(tenista.id) } returns tenista

        val result = services.getTenistaById(tenista.id).value

        assertAll(
            { assertEquals("TestNombre", result.nombre) },
        )

        verify(exactly = 1) { cache.get(tenista.id) }
        verify(exactly = 1) { repository.getTenistaById(tenista.id) }

    }

    @Test
    fun getTenistaByIdNotFound() {

        every { cache.get(tenista.id) } returns null
        every { repository.getTenistaById(tenista.id) } returns null

        val result = services.getTenistaById(tenista.id).error

        assertTrue(result is TenistaError.TenistaNotFound)

        verify(exactly = 1) { cache.get(tenista.id) }
        verify(exactly = 1) { repository.getTenistaById(tenista.id) }

    }

    @Test
    fun getTenistaByNombreInRepo() {

        every { repository.getTenistaById(tenista.id) } returns tenista

        val result = services.getTenistaByNombre(tenista.nombre).value

        assertAll(
            { assertEquals("TestNombre", result.nombre) },
        )

        verify(exactly = 1) { repository.getTenistaByName(tenista.nombre) }
    }

    @Test
    fun getTenistaByNombreNotFound(){

        every { repository.getTenistaById(tenista.id) } returns null

        val result = services.getTenistaByNombre(tenista.nombre).error

        assertTrue(result is TenistaError.TenistaNotFound)

        verify(exactly = 1) { repository.getTenistaByName(tenista.nombre) }
    }
    @Test
    fun createTenista() {

        val result = services.createTenista(tenista).value

        assertAll(
            { assertEquals("TestNombre", result.nombre) },
        )

        verify(exactly = 1) { cache.put(tenista.id,tenista) }
        verify(exactly = 1) { repository.saveTenista(tenista) }
    }

    @Test
    fun updateTenistaOk() {

        every { repository.updateTenista(tenista) } returns tenista
        every { cache.put(tenista.id,tenista) } just Runs

        val result = services.updateTenista(tenista).value

        assertAll(
            { assertEquals("TestNombre", result.nombre) },
        )

        verify(exactly = 1) { cache.put(tenista.id,tenista) }
        verify(exactly = 1) { repository.updateTenista(tenista) }
    }

    @Test
    fun updateTenistaNotUpdated() {

        every { repository.updateTenista(tenista) } returns null

        val result = services.updateTenista(tenista).error

        assertTrue(result is TenistaError.TenistaNotUpdated)

        verify(exactly = 0) { cache.put(tenista.id,tenista) }
        verify(exactly = 1) { repository.updateTenista(tenista) }
    }

    @Test
    fun deleteTenistaById() {

        every { repository.deleteById(tenista.id) } returns tenista
        every { cache.remove(tenista.id) } just Runs

        val result = services.deleteTenistaById(tenista.id).value

        assertAll(
            { assertEquals(Unit, result) }
        )

        verify(exactly = 1) { cache.remove(tenista.id) }
        verify(exactly = 1) { repository.deleteById(tenista.id) }
    }

    @Test
    fun deleteTenistaByIdNotDeleted() {

        every { repository.deleteById(tenista.id) } returns null

        val result = services.deleteTenistaById(tenista.id).error

        assertTrue(result is TenistaError.TenistaNotDeleted)

        verify(exactly = 0) { cache.remove(tenista.id) }
        verify(exactly = 1) { repository.deleteById(tenista.id) }
    }

    @Test
    fun readCSVOk() {
        val file=File("data/data.csv") //TODO Usar fichero temporal **JAVI RUIZ**

        val tenistas= listOf(tenista)

        every { storage.readCsv(file).value } returns tenistas
        every { repository.saveTenista(tenista) } returns tenista

        val result=services.readCSV(file).value

        assertAll(
            { assertEquals(result,tenistas) },
            { assertEquals(result.size,tenistas.size) }
        )

        verify(exactly = 1) { storage.readCsv(file) }
        verify(exactly = 1) { repository.saveTenista(tenista) }
    }

    @Test
    fun readCSVFileNoRead() {
        val file=File("data/data.csv") //TODO Usar fichero temporal **JAVI RUIZ**

        every { storage.readCsv(file).error } returns   FileError.FileReadingError("Error loading tenistas from file: $file")


        val result=services.readCSV(file).error

        assertTrue(result is FileError.FileReadingError)

        verify(exactly = 1) { storage.readCsv(file) }
        verify(exactly = 0) { repository.saveTenista(tenista) }
    }

    @Test
    fun writeCSVOk() {
        val file=File("data/testCSV.csv") //TODO Usar fichero temporal **JAVI RUIZ**

        val tenistas= listOf(tenista)

        every { storage.storeCsv(file,tenistas).value } returns Unit

        val result=services.writeCSV(file,tenistas).value

        assertAll(
            { assertEquals(result,Unit) }
        )

        verify(exactly = 1) { storage.storeCsv(file,tenistas) }

    }

    @Test
    fun writeCSVFileError() {
        val file=File("data/testCSV.csv") //TODO Usar fichero temporal **JAVI RUIZ**

        val tenistas= listOf(tenista)

        every { storage.storeCsv(file,tenistas).error } returns FileError.FileWritingError("Error storing tenistas into file: $file")

        val result=services.writeCSV(file,tenistas).error

        assertTrue(result is FileError.FileWritingError)

        verify(exactly = 1) { storage.storeCsv(file,tenistas) }

    }

    @Test
    fun writeJsonOk() {
        val file=File("data/testJSON.json") //TODO Usar fichero temporal **JAVI RUIZ**

        val tenistas= listOf(tenista)

        every { storage.storeJson(file,tenistas).value } returns Unit

        val result=services.writeJson(file,tenistas).value

        assertAll(
            { assertEquals(result,Unit) }
        )

        verify(exactly = 1) { storage.storeJson(file,tenistas) }
    }

    @Test
    fun writeJsonFileError(){
        val file=File("data/testJSON.json") //TODO Usar fichero temporal **JAVI RUIZ**

        val tenistas= listOf(tenista)

        every { storage.storeJson(file,tenistas).error } returns FileError.FileWritingError("Error storing tenistas into $file")

        val result=services.writeJson(file,tenistas).error

        assertTrue(result is FileError.FileWritingError)

        verify(exactly = 1) { storage.storeJson(file,tenistas) }
    }

    @Test
    fun writeXml() {
        val file=File("data/testXml.xml") //TODO Usar fichero temporal **JAVI RUIZ**

        val tenistas= listOf(tenista)

        every { storage.storeXml(file,tenistas).value } returns Unit

        val result=services.writeXml(file,tenistas).value

        assertAll(
            { assertEquals(result,Unit) }
        )

        verify(exactly = 1) { storage.storeXml(file,tenistas) }
    }
    @Test
    fun writeXmlFileError(){
        val file=File("data/testXml.xml") //TODO Usar fichero temporal **JAVI RUIZ**

        val tenistas= listOf(tenista)

        every { storage.storeXml(file,tenistas).error } returns FileError.FileWritingError("Error storing tenistas into $file")

        val result=services.writeXml(file,tenistas).error

        assertTrue(result is FileError.FileWritingError)

        verify(exactly = 1) { storage.storeXml(file,tenistas) }
    }
}