package pila.money

data class MoneyBag(
    private val monies: MutableMap<Currency, MutableList<Money>> = mutableMapOf(),
) {
    fun put(money: Money) =
        monies
            .getOrPut(money.currency) { mutableListOf() }
            .add(money)

    fun remove(money: Money): Boolean {
        val list = monies[money.currency] ?: return false
        val removed = list.remove(money)

        if (list.isEmpty()) {
            monies.remove(money.currency)
        }

        return removed
    }

    fun getAll(currency: Currency): List<Money> =
        monies[currency]
            ?.toList()
            .orEmpty()

    fun getAll(): List<Money> =
        monies.values
            .flatten()
}
