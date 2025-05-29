package pila.money

interface MonetaryOperations<T : Monetary> {
    infix fun sum(secondAmount: T): T

    infix fun minus(secondAmount: T): T

    fun display(): String

    infix fun split(parts: Int): List<T>
}
