package pila.money

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import pila.money.Money.Companion.toMoney
import java.math.BigDecimal

class MoneyTest :
    DescribeSpec({

        describe("Money") {
            context("Create factories and operations") {
                it("From BigDecimal with currency") {
                    val money =
                        BigDecimal("10.22") toMoney BRL

                    money.amount shouldBe BigDecimal("10.22")
                    money.currency shouldBe BRL
                }

                it("From BigDecimal") {
                    System.setProperty("pila.currency.code", "BRL")
                    System.setProperty("pila.currency.displayName", "real")
                    System.setProperty("pila.currency.symbol", "R$")

                    val money =
                        BigDecimal("10.22").toMoney()

                    money.amount shouldBe BigDecimal("10.22")
                    money.currency shouldBe BRL
                }

                it("From String with currency") {
                    val money =
                        "10.22" toMoney BRL

                    money.amount shouldBe BigDecimal("10.22")
                    money.currency shouldBe BRL
                }

                it("From String") {
                    System.setProperty("pila.currency.code", "BRL")
                    System.setProperty("pila.currency.displayName", "real")
                    System.setProperty("pila.currency.symbol", "R$")

                    val money =
                        "10.22".toMoney()

                    money.amount shouldBe BigDecimal("10.22")
                    money.currency shouldBe BRL
                }

                it("Should sum two moneys") {
                    val money1 =
                        BigDecimal("10.22") toMoney BRL
                    val money2 =
                        BigDecimal("10.22") toMoney BRL

                    val money3 = money1 sum money2

                    money3.amount shouldBe BigDecimal("20.44")
                    money3.currency shouldBe BRL
                }

                it("Should minus two moneys") {
                    val money1 =
                        BigDecimal("10.24") toMoney BRL
                    val money2 =
                        BigDecimal("10.22") toMoney BRL

                    val money3 = money1 minus money2

                    money3.amount shouldBe BigDecimal("0.02")
                    money3.currency shouldBe BRL
                }

                it("Should display") {
                    val money1 =
                        BigDecimal("10.24") toMoney BRL

                    money1.display() shouldBe "R$ 10.24 BRL"
                }

                it("Should convert to cents") {
                    val money1 =
                        BigDecimal("1.24") toMoney BRL

                    money1.toCents() shouldBe 124
                }

                it("Should split the money") {
                    val money1 =
                        BigDecimal("1.00") toMoney BRL

                    val result = money1 split 3

                    result.map { it.amount } shouldContainAll
                        listOf(
                            BigDecimal("0.33"),
                            BigDecimal("0.33"),
                            BigDecimal("0.34"),
                        )

                    result.map { it.currency } shouldContainAll
                        listOf(
                            BRL,
                            BRL,
                            BRL,
                        )

                    val money2 =
                        BigDecimal("10.00") toMoney BRL

                    val result2 = money2 split 3

                    result2.map { it.amount } shouldContainAll
                        listOf(
                            BigDecimal("3.33"),
                            BigDecimal("3.33"),
                            BigDecimal("3.34"),
                        )

                    val result3 = money2 split 4

                    result3.map { it.amount } shouldContainAll
                        listOf(
                            BigDecimal("2.50"),
                            BigDecimal("2.50"),
                            BigDecimal("2.50"),
                            BigDecimal("2.50"),
                        )
                }
            }
        }
    })
