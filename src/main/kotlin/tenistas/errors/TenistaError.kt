package tenistas.errors

sealed class TenistaError(message: String) {
    class TenistaNotFound(message: String): TenistaError(message)
}