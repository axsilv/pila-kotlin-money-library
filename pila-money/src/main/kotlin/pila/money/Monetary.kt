package pila.money

import java.math.BigDecimal

interface Monetary {
    val currency: Currency
    val amount: BigDecimal
}
