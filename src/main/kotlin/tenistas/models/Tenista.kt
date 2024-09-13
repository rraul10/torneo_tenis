package tenistas.models

import java.time.LocalDate

data class Tenista(
    val id: Long = -1,
    val nombre: String,
    val pais: String,
    val altura: Int,
    val peso: Int,
    val puntos: Int,
    val mano: Mano,
    val fecha: LocalDate,
) {
    enum class Mano {
        DIESTRO, ZURODO
    }
}