package pila.money

data class MoneyBag(
    private val monies: MutableMap<Currency, MutableList<Money>> = mutableMapOf(),
    private val observers: List<MoneyObserver> = emptyList(),
) {
    fun put(money: Money) =
        withObservers {
            monies
                .getOrPut(money.currency) { mutableListOf() }
                .add(money)
        }

    fun remove(money: Money): Boolean =
        withObservers {
            val list = monies[money.currency] ?: return@withObservers false
            val removed = list.remove(money)

            if (list.isEmpty()) {
                monies.remove(money.currency)
            }

            removed
        }

    fun getAll(currency: Currency): List<Money> =
        monies[currency]
            ?.toList()
            .orEmpty()

    fun getAll(): List<Money> =
        monies.values
            .flatten()

    private infix fun <T> withObservers(exec: () -> T): T {
        val result = exec.invoke()
        observers.forEach { it.notify(monies) }
        return result
    }
}
