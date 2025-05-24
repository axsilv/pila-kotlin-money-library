package pila.money

import pila.money.Money.Companion.toMoney
import java.math.RoundingMode
import java.math.RoundingMode.HALF_UP

fun List<Money>.average(roundingMode: RoundingMode = HALF_UP): Money {
    require(isNotEmpty()) { "Cannot calculate average of empty list" }

    val currency = first().currency

    require(all { it.currency == currency }) {
        "Cannot calculate average for multiple currencies"
    }

    val sum = reduce { acc, money -> acc sum money }

    val averageAmount =
        sum.amount.divide(
            size.toBigDecimal(),
            sum.amount.scale(),
            roundingMode,
        )

    return averageAmount.toMoney(currency)
}

fun List<Money>.sum(): Money {
    require(isNotEmpty()) { "Cannot sum an empty list" }

    val currency = first().currency

    require(all { it.currency == currency }) {
        "Not possible to calculate sum value for two or more currencies"
    }

    return reduce { acc, money -> acc sum money }
}
