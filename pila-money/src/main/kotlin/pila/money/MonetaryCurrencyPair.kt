package pila.money

data class MonetaryCurrencyPair(
    val base: MonetaryCurrency,
    val counter: MonetaryCurrency,
) : Exchanger<MonetaryCurrencyPair, MonetaryCurrency> {
    override fun invert(): MonetaryCurrencyPair =
        MonetaryCurrencyPair(
            base = counter,
            counter = base,
        )

    override fun code(): String = "${base.code}/${counter.code}"

    override fun contains(currency: MonetaryCurrency): Boolean = currency == base || currency == counter

    override fun isSameAs(
        other: MonetaryCurrencyPair,
        ignoreOrder: Boolean,
    ): Boolean =
        if (ignoreOrder) {
            (base == other.base && counter == other.counter) ||
                (base == other.counter && counter == other.base)
        } else {
            this == other
        }

    override fun matches(
        baseCode: String,
        counterCode: String,
    ): Boolean = base.code == baseCode && counter.code == counterCode
}
