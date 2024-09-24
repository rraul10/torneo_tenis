package tenistas.storage

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import tenistas.errors.FileError
import tenistas.models.Tenista
import java.io.File
import java.time.LocalDate
import java.util.*


class TenistasStorageImplTest {
    private val storage = TenistasStorageImpl()


    @Test
    fun readCsvOk() {
        val file= File(javaClass.classLoader.getResource("data.csv").file)

        val result= storage.readCsv(file)

        assertTrue(result.isOk)

    }

    @Test
    fun readCsvMaxColumns() {
        val file= File(javaClass.classLoader.getResource("dataMaxColumns.csv").file)

        val result= storage.readCsv(file).isErr

        assertTrue(!result)

    }

    @Test
    fun readCsvMaxLines() {
        val file= File(javaClass.classLoader.getResource("dataMaxLines.csv").file)

        val result= storage.readCsv(file)

        assertTrue(result.isErr)

    }

    @Test
    fun storeCsvOk() {
        val file=File("data/testCSV.csv")

        val lista= listOf(
            Tenista(
            id = UUID.fromString("652923e4-c06c-4ca0-adb8-e6fbd141d32e"),
            nombre = "TestNombre",
            pais="TestPais",
            altura= 180,
            peso= 70,
            puntos= 10,
            mano= "ZURDO",
            fecha_nacimiento = LocalDate.of(2024,1,1)
            )
        )

        val result= storage.storeCsv(file,lista)

        assertAll(
            { assertTrue(result.isOk) }
        )

    }

    @Test
    fun storeJsonOk() {
        val file=File("data/testJson.json")

        val lista= listOf(
            Tenista(
                id = UUID.fromString("652923e4-c06c-4ca0-adb8-e6fbd141d32e"),
                nombre = "TestNombre",
                pais="TestPais",
                altura= 180,
                peso= 70,
                puntos= 10,
                mano= "ZURDO",
                fecha_nacimiento = LocalDate.of(2024,1,1)
            )
        )

        val result= storage.storeJson(file,lista)

        assertAll(
            { assertTrue(result.isOk) }
        )
    }

    @Test
    fun storeXmlOk() {
        val file=File("data/testXml.xml")

        val lista= listOf(
            Tenista(
                id = UUID.fromString("652923e4-c06c-4ca0-adb8-e6fbd141d32e"),
                nombre = "TestNombre",
                pais="TestPais",
                altura= 180,
                peso= 70,
                puntos= 10,
                mano= "ZURDO",
                fecha_nacimiento = LocalDate.of(2024,1,1)
            )
        )

        val result= storage.storeXml(file,lista)

        assertAll(
            { assertTrue(result.isOk) }
        )
    }
}