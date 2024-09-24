package tenistas.validators

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import tenistas.errors.ArgsErrors
import tenistas.errors.TenistaError

class ArgsValidatorsKtTest {

    @Test
    fun validateArgsEntrada() {
        val csvFile = javaClass.classLoader.getResource("data.csv").file
        val result = validateArgsEntrada(csvFile)
        assertEquals(csvFile, result.value)
    }

    @Test
    fun validateArgsEntradaNotCsv() {
        val csvFile = javaClass.classLoader.getResource("test.json").file
        val result = validateArgsEntrada(csvFile).isErr
        assertTrue(!result)

    }

    @Test
    fun validateArgsEntradaEmpty() {
        val csvFile = javaClass.classLoader.getResource("").file
        val result = validateArgsEntrada(csvFile).isErr
        assertTrue(!result)
    }

    @Test
    fun validateArgsEntradaNotExists() {
        val csvFile = javaClass.classLoader.getResource("jaja.csv").file
        val result = validateArgsEntrada(csvFile).isErr
        assertTrue(!result)
    }

    @Test
    fun validateArgsSalida() {
        val validInput = "path/to/valid/file.json"
        val result = validateArgsSalida(validInput)
        assertEquals("argumento de salida valido", result.value)
    }
}
