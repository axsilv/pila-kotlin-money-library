package pila.money

interface Exchanger<T, C> {
    fun invert(): T

    fun code(): String

    fun contains(currency: C): Boolean

    fun isSameAs(
        other: MonetaryCurrencyPair,
        ignoreOrder: Boolean = false,
    ): Boolean

    fun matches(
        baseCode: String,
        counterCode: String,
    ): Boolean
}
