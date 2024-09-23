package tenistas.service

import com.github.michaelbull.result.Result
import tenistas.errors.FileError
import tenistas.errors.TenistaError
import tenistas.models.Tenista
import java.io.File
import java.util.UUID

interface TenistasService {
    fun getAllTenistas(): Result<List<Tenista>, TenistaError>
    fun getTenistaById(id: UUID): Result<Tenista, TenistaError>
    fun getTenistaByNombre(nombre: String): Result<Tenista, TenistaError>
    fun createTenista(tenista: Tenista): Result<Tenista, TenistaError>
    fun updateTenista(tenista: Tenista): Result<Tenista, TenistaError>
    fun deleteTenistaById(id: UUID): Result<Unit, TenistaError>
    fun readCSV(file: File): Result<List<Tenista>, FileError>
    fun writeCSV(file: File, tenistas: List<Tenista>): Result<Unit, FileError>
    fun writeJson(file: File, tenistas: List<Tenista>): Result<Unit, FileError>
    fun writeXml(file: File, tenistas: List<Tenista>): Result<Unit, FileError>
}