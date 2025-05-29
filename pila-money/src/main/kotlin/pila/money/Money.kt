package pila.money

import pila.money.MoneyException.MonetaryOperationSplitException
import java.math.BigDecimal
import java.math.BigDecimal.ZERO
import java.math.RoundingMode
import java.math.RoundingMode.HALF_UP

private const val PILA_CURRENCY_CODE = "pila.currency.code"

private const val PILA_CURRENCY_DISPLAY_NAME = "pila.currency.displayName"

private const val PILA_CURRENCY_SYMBOL = "pila.currency.symbol"

class Money private constructor(
    override val currency: Currency,
    override val amount: BigDecimal,
) : Monetary,
    MonetaryOperations<Money> {
    companion object {
        infix fun String.toMoney(currency: Currency): Money =
            Money(
                currency = currency,
                amount =
                    check(
                        BigDecimal(this),
                        currency,
                    ),
            )

        fun String.toMoney(): Money = this.toMoney(currency = systemCurrency())

        infix fun BigDecimal.toMoney(currency: Currency): Money =
            Money(
                currency = currency,
                amount = check(this, currency),
            )

        fun BigDecimal.toMoney() = this.toMoney(currency = systemCurrency())

        private fun systemCurrency(): MonetaryCurrency =
            MonetaryCurrency(
                code = fromEnv(PILA_CURRENCY_CODE),
                displayName = fromEnv(PILA_CURRENCY_DISPLAY_NAME),
                symbol = fromEnv(PILA_CURRENCY_SYMBOL),
            )

        private fun fromEnv(name: String): String =
            System.getProperty(name)
                ?: throw RuntimeException("System env for pila was not found $name")

        private fun check(
            amount: BigDecimal,
            currency: Currency,
        ): BigDecimal {
            check(
                amount.scale() == currency.scale,
            ) {
                "Monetary amounts must be ${currency.scale} digit decimal scale"
            }

            return amount
        }
    }

    override infix fun sum(secondAmount: Money): Money {
        require(currency.code == secondAmount.currency.code) {
            "Cannot sum different currencies: ${currency.code} and ${secondAmount.currency.code}"
        }

        return Money(
            currency = currency,
            amount = amount + secondAmount.amount,
        )
    }

    override infix fun minus(secondAmount: Money): Money {
        require(currency.code == secondAmount.currency.code) {
            "Cannot minus different currencies: ${currency.code} and ${secondAmount.currency.code}"
        }

        return Money(
            currency = currency,
            amount = amount - secondAmount.amount,
        )
    }

    override fun display() = "${currency.symbol} $amount ${currency.code}"

    fun toCents(roundingMode: RoundingMode = HALF_UP): Long =
        amount
            .setScale(2, roundingMode)
            .multiply(BigDecimal("100"))
            .longValueExact()

    override infix fun split(parts: Int): List<Money> {
        require(parts > 0) { "Number of parts must be positive" }

        val totalCents = this.toCents()
        val baseCents = totalCents / parts
        val remainder = (totalCents % parts).toInt()

        return List(parts) { partIndex ->
            val cents = baseCents + if (partIndex < remainder) 1 else 0
            (
                BigDecimal(cents)
                    .divide(BigDecimal(100))
                    .setScale(currency.scale)
            ) toMoney currency
        }.also { it.validateSplit() }
    }

    private fun List<Money>.validateSplit() =
        map { it.amount }
            .fold(ZERO) { acc, item -> acc + item }
            .let {
                if (it != amount) {
                    throw MonetaryOperationSplitException(
                        "Monetary operation split exception: $it differ $amount",
                    )
                }
            }
}
