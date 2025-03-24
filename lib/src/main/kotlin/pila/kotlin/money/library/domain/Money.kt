package pila.kotlin.money.library.domain

import java.math.BigDecimal
import java.math.RoundingMode
import java.math.RoundingMode.HALF_UP

class Money private constructor(
    val currency: Currency,
    val amount: BigDecimal,
) {
    companion object {
        fun String.toMoney(currency: Currency) =
            Money(
                currency = currency,
                amount = check(BigDecimal(this)),
            )

        fun String.toMoney() =
            this.toMoney(
                currency =
                    Currency(
                        code = System.getenv("pila.currency.code"),
                        displayName = System.getenv("pila.currency.displayName"),
                        symbol = System.getenv("pila.currency.symbol"),
                    ),
            )

        fun BigDecimal.toMoney(currency: Currency) =
            Money(
                currency = currency,
                amount = check(this),
            )

        fun BigDecimal.toMoney() =
            this.toMoney(
                currency =
                    Currency(
                        code = System.getenv("pila.currency.code"),
                        displayName = System.getenv("pila.currency.displayName"),
                        symbol = System.getenv("pila.currency.symbol"),
                    ),
            )

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
}
