package pila.kotlin.money.library.domain

import java.math.BigDecimal
import java.math.RoundingMode
import java.math.RoundingMode.HALF_UP

private const val PILA_CURRENCY_CODE = "pila.currency.code"

private const val PILA_CURRENCY_DISPLAY_NAME = "pila.currency.displayName"

private const val PILA_CURRENCY_SYMBOL = "pila.currency.symbol"

class Money private constructor(
    val currency: Currency,
    val amount: BigDecimal,
) {
    companion object {
        infix fun String.toMoney(currency: Currency): Money =
            Money(
                currency = currency,
                amount = check(BigDecimal(this)),
            )

        fun String.toMoney(): Money = this.toMoney(currency = systemCurrency())

        infix fun BigDecimal.toMoney(currency: Currency): Money =
            Money(
                currency = currency,
                amount = check(this),
            )

        fun BigDecimal.toMoney() = this.toMoney(currency = systemCurrency())

        private fun systemCurrency(): Currency =
            Currency(
                code = fromEnv(PILA_CURRENCY_CODE),
                displayName = fromEnv(PILA_CURRENCY_DISPLAY_NAME),
                symbol = fromEnv(PILA_CURRENCY_SYMBOL),
            )

        private fun fromEnv(name: String): String =
            System.getProperty(name)
                ?: throw RuntimeException("System env for pila was not found $name")

        private fun check(amount: BigDecimal): BigDecimal {
            check(
                amount.scale() == 2,
            ) {
                "Monetary amounts must be 2 digit decimal scale"
            }

            return amount
        }
    }

    infix fun sum(secondAmount: Money): Money {
        require(currency.code == secondAmount.currency.code) {
            "Cannot sum different currencies: ${currency.code} and ${secondAmount.currency.code}"
        }

        return Money(
            currency = currency,
            amount = amount + secondAmount.amount,
        )
    }

    infix fun minus(secondAmount: Money): Money {
        require(currency.code == secondAmount.currency.code) {
            "Cannot minus different currencies: ${currency.code} and ${secondAmount.currency.code}"
        }

        return Money(
            currency = currency,
            amount = amount - secondAmount.amount,
        )
    }

    fun display() = "${currency.symbol} $amount ${currency.code}"

    fun toCents(roundingMode: RoundingMode = HALF_UP): Long =
        amount
            .setScale(2, roundingMode)
            .multiply(BigDecimal("100"))
            .longValueExact()

    infix fun split(parts: Int): List<Money> {
        require(parts > 0) { "Number of parts must be positive" }

        val totalCents = this.toCents()
        val baseCents = totalCents / parts
        val remainder = (totalCents % parts).toInt()

        return List(parts) { partIndex ->
            val cents = baseCents + if (partIndex < remainder) 1 else 0
            (
                BigDecimal(cents)
                    .divide(BigDecimal(100))
                    .setScale(2)
            ) toMoney currency
        }
    }
}
