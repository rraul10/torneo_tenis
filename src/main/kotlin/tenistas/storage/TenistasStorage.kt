package tenistas.storage

import com.github.michaelbull.result.Result
import tenistas.errors.FileError
import tenistas.models.Tenista
import java.io.File

interface TenistasStorage {
    fun store(file: File, tenistas: List<Tenista>): Result<Unit, FileError>
}