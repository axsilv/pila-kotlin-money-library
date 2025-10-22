package pila.money

fun interface MoneyObserver {
    fun notify(monies: Map<Currency, List<Money>>)
}
