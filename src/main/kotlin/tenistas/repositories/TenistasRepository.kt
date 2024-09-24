package tenistas.repositories

import tenistas.models.Tenista

interface TenistasRepository {
    fun getAllTenistas(): List<Tenista>
    fun getTenistaById(id: Long): Tenista?
    fun getTenistaByName(nombre: String): Tenista?
    fun saveTenista(tenista: Tenista): Tenista
    fun updateTenista(id: UUID, tenista: Tenista): Tenista?
    fun deleteById(id: UUID): Tenista?

}