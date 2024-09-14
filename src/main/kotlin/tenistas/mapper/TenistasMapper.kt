package tenistas.mapper

import Locale.retunrDateTimeString
import Locale.returnDateString
import database.Tenistas_Entity
import org.lighthousegames.logging.logging
import tenistas.dto.TenistaDto
import tenistas.models.Tenista

val logger = logging()
fun Tenistas_Entity.toTenista(): Tenista {
    logger.debug { "Mapeando Tenistas_Entity a Tenista" }
    return Tenista(
        id = this.id,
        nombre = this.nombre,
        pais = this.pais,
        altura = this.altura.toInt(),
        peso = this.peso.toInt(),
        puntos = this.puntos.toInt(),
        mano = this.mano,
        fecha_nacimiento = this.fecha_nacimiento.returnDateString(),
        createdAt = this.created_at.retunrDateTimeString(),
        updatedAt = this.upadated_at.retunrDateTimeString()
    )
}

fun Tenista.toTenistas_Entity(): Tenistas_Entity {
    logger.debug { "Mapeando Tenista a Tenistas_Entity" }
    return Tenistas_Entity(
        id = this.id,
        nombre = this.nombre,
        pais = this.pais,
        altura = this.altura.toLong(),
        peso = this.peso.toLong(),
        puntos = this.puntos.toLong(),
        mano = this.mano,
        fecha_nacimiento = this.fecha_nacimiento.toString(),
        created_at = this.createdAt.toString(),
        upadated_at = this.updatedAt.toString()
    )
}

fun TenistaDto.toTenista(): Tenista {
    logger.debug { "Mapeando TenistaDto a Tenista" }
    return Tenista(
        id = this.id.toLongOrNull()?: 0,
        nombre = this.nombre,
        pais = this.pais,
        altura = this.altura.toInt(),
        peso = this.peso.toInt(),
        puntos = this.puntos.toInt(),
        mano = this.mano,
        fecha_nacimiento = this.fecha_nacimiento.returnDateString(),
        createdAt = this.created_at.retunrDateTimeString(),
        updatedAt = this.updated_at.retunrDateTimeString()
    )
}

fun Tenista.toTenistaDto(): TenistaDto {
    logger.debug { "Mapeando Tenista a TenistaDto" }
    return TenistaDto(
        id = this.id.toString(),
        nombre = this.nombre,
        pais = this.pais,
        altura = this.altura.toString(),
        peso = this.peso.toString(),
        puntos = this.puntos.toString(),
        mano = this.mano,
        fecha_nacimiento = this.fecha_nacimiento.toString(),
        created_at = this.createdAt.toString(),
        updated_at = this.updatedAt.toString()
    )
}