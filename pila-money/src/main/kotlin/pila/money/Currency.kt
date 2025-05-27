package pila.money

interface Currency {
    val code: String
    val symbol: String
    val scale: Int
}
