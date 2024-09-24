package tenistas.validators

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import tenistas.errors.ArgsErrors
import tenistas.errors.CsvErrors

class CsvValidatorKtTest {

    @Test
    fun validateCsvFormat() {
        val csvFile = javaClass.classLoader.getResource("data.csv").file
        val result = validateCsvFormat(csvFile)
        assertEquals(Unit, result.value)
    }

    @Test
    fun validateCsvFormatIncorrectLines() {
        val csvFile = javaClass.classLoader.getResource("dataMaxLines.csv").file
        val result = validateCsvFormat(csvFile).isErr
        assertTrue(result)
    }

    @Test
    fun validateCsvFormatIncorrectColumns() {
        val csvFile = javaClass.classLoader.getResource("dataMaxColumns.csv").file
        val result = validateCsvFormat(csvFile).isErr
        assertTrue(result)
    }

    @Test
    fun validateCsvEntries() {
        val validEntries = listOf("1", "Novak Djokovic", "Serbia", "188", "77", "12030", "DIESTRO", "1987-05-22")
        val result = validateCsvEntries(validEntries)
        assertEquals(validEntries, result.value)
    }

    @Test
    fun validateCsvEntriesEmptyID() {
        val validEntries = listOf("", "Novak Djokovic", "Serbia", "188", "77", "12030", "DIESTRO", "1987-05-22")
        val result = validateCsvEntries(validEntries).isErr
        assertTrue(result)
    }

    @Test
    fun validateCsvEntriesIncorrectFormatID() {
        val validEntries = listOf("a" ,"Novak Djokovic", "Serbia", "188", "77", "12030", "DIESTRO", "1987-05-22")
        val result = validateCsvEntries(validEntries).isErr
        assertTrue(result)
    }

    @Test
    fun validateCsvEntriesIncorrectFormatIDInt() {
        val validEntries = listOf("-1" ,"Novak Djokovic", "Serbia", "188", "77", "12030", "DIESTRO", "1987-05-22")
        val result = validateCsvEntries(validEntries).isErr
        assertTrue(result)
    }

    @Test
    fun validateCsvEntriesEmptyName() {
        val validEntries = listOf("1", "", "Serbia", "188", "77", "12030", "DIESTRO", "1987-05-22")
        val result = validateCsvEntries(validEntries).isErr
        assertTrue(result)
    }

    @Test
    fun validateCsvEntriesIncorrectFormatName() {
        val validEntries = listOf("1", "1", "Serbia", "188", "77", "12030", "DIESTRO", "1987-05-22")
        val result = validateCsvEntries(validEntries).isErr
        assertTrue(result)
    }

    @Test
    fun validateCsvEntriesEmptyCountry() {
        val validEntries = listOf("1", "Novak Djokovic", "", "188", "77", "12030", "DIESTRO", "1987-05-22")
        val result = validateCsvEntries(validEntries).isErr
        assertTrue(result)
    }

    @Test
    fun validateCsvEntriesIncorrectFormatCountry() {
        val validEntries = listOf("1", "Novak Djokovic", "Serbia1", "188", "77", "12030", "DIESTRO", "1987-05-22")
        val result = validateCsvEntries(validEntries).isErr
        assertTrue(result)
    }

    @Test
    fun validateCsvEntriesEmptyHeight() {
        val validEntries = listOf("1", "Novak Djokovic", "Serbia", "", "77", "12030", "DIESTRO", "1987-05-22")
        val result = validateCsvEntries(validEntries).isErr
        assertTrue(result)
    }

    @Test
    fun validateCsvEntriesIncorrectFormatHeight() {
        val validEntries = listOf("1", "Novak Djokovic", "Serbia", "jaja", "77", "12030", "DIESTRO", "1987-05-22")
        val result = validateCsvEntries(validEntries).isErr
        assertTrue(result)
    }

    @Test
    fun validateCsvEntriesNegativeHeight() {
        val validEntries = listOf("1", "Novak Djokovic", "Serbia", "-1", "77", "12030", "DIESTRO", "1987-05-22")
        val result = validateCsvEntries(validEntries).isErr
        assertTrue(result)
    }

    @Test
    fun validateCsvEntriesEmptyWeight() {
        val validEntries = listOf("1", "Novak Djokovic", "Serbia", "188", "", "12030", "DIESTRO", "1987-05-22")
        val result = validateCsvEntries(validEntries).isErr
        assertTrue(result)
    }

    @Test
    fun validateCsvEntriesIncorrectFormatWeight() {
        val validEntries = listOf("1", "Novak Djokovic", "Serbia", "188", "jaja", "12030", "DIESTRO", "1987-05-22")
        val result = validateCsvEntries(validEntries).isErr
        assertTrue(result)
    }

    @Test
    fun validateCsvEntriesNegativeWeight() {
        val validEntries = listOf("1", "Novak Djokovic", "Serbia", "188", "-1", "12030", "DIESTRO", "1987-05-22")
        val result = validateCsvEntries(validEntries).isErr
        assertTrue(result)
    }

    @Test
    fun validateCsvEntriesEmptyPoints() {
        val validEntries = listOf("1", "Novak Djokovic", "Serbia", "188", "77", "", "DIESTRO", "1987-05-22")
        val result = validateCsvEntries(validEntries).isErr
        assertTrue(result)
    }

    @Test
    fun validateCsvEntriesIncorrectFormatPoints() {
        val validEntries = listOf("1", "Novak Djokovic", "Serbia", "188", "77", "jaja", "DIESTRO", "1987-05-22")
        val result = validateCsvEntries(validEntries).isErr
        assertTrue(result)
    }

    @Test
    fun validateCsvEntriesNegativePoints() {
        val validEntries = listOf("1", "Novak Djokovic", "Serbia", "188", "77", "-1", "DIESTRO", "1987-05-22")
        val result = validateCsvEntries(validEntries).isErr
        assertTrue(result)
    }

    @Test
    fun validateCsvEntriesEmptyHand() {
        val validEntries = listOf("1", "Novak Djokovic", "Serbia", "188", "77", "12030", "", "1987-05-22")
        val result = validateCsvEntries(validEntries).isErr
        assertTrue(result)
    }

    @Test
    fun validateCsvEntriesIncorrectFormatHand() {
        val validEntries = listOf("1", "Novak Djokovic", "Serbia", "188", "77", "12030", "jaja", "1987-05-22")
        val result = validateCsvEntries(validEntries).isErr
        assertTrue(result)
    }

    @Test
    fun validateCsvEntriesEmptyDate() {
        val validEntries = listOf("1", "Novak Djokovic", "Serbia", "188", "77", "12030", "DIESTRO", "")
        val result = validateCsvEntries(validEntries).isErr
        assertTrue(result)
    }

    @Test
    fun validateCsvEntriesIncorrectFormatDate() {
        val validEntries = listOf("1", "Novak Djokovic", "Serbia", "188", "77", "12030", "DIESTRO", "22-05-1977")
        val result = validateCsvEntries(validEntries).isErr
        assertTrue(result)
    }

    @Test
    fun validateCsvEntriesSuperiorDate() {
        val validEntries = listOf("1", "Novak Djokovic", "Serbia", "188", "77", "12030", "DIESTRO", "2025-05-22")
        val result = validateCsvEntries(validEntries).isErr
        assertTrue(result)
    }

}