package pila.money

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import pila.money.Money.Companion.toMoney
import java.math.BigDecimal

class MoneyExtensionsTest :
    DescribeSpec({

        val usd = Currency("USD", "US Dollar", "$", 2)
        val eur = Currency("EUR", "Euro", "€", 2)

        describe("List<Money>.average") {

            context("when all monies have the same currency") {
                it("should return the correct average") {
                    val list =
                        listOf(
                            BigDecimal("10.00").toMoney(usd),
                            BigDecimal("20.00").toMoney(usd),
                            BigDecimal("30.00").toMoney(usd),
                        )

                    list.average().amount shouldBe BigDecimal("20.00").toMoney(usd).amount
                }

                it("should handle average with decimal result") {
                    val list =
                        listOf(
                            BigDecimal("10.00").toMoney(usd),
                            BigDecimal("11.00").toMoney(usd),
                        )

                    list.average().amount shouldBe BigDecimal("10.50")
                }
            }

            context("when the list is empty") {
                it("should throw IllegalArgumentException") {
                    val emptyList = emptyList<Money>()

                    val exception =
                        shouldThrow<IllegalArgumentException> {
                            emptyList.average()
                        }

                    exception.message shouldBe "Cannot calculate average of empty list"
                }
            }

            context("when the list has different currencies") {
                it("should throw IllegalArgumentException") {
                    val mixedCurrencies =
                        listOf(
                            BigDecimal("10.00").toMoney(usd),
                            BigDecimal("20.00").toMoney(eur),
                        )

                    val exception =
                        shouldThrow<IllegalArgumentException> {
                            mixedCurrencies.average()
                        }

                    exception.message shouldBe "Cannot calculate average for multiple currencies"
                }
            }
        }

        describe("List<Money>.sum") {

            context("when all monies have the same currency") {
                it("should return the correct sum") {
                    val list =
                        listOf(
                            BigDecimal("10.00").toMoney(usd),
                            BigDecimal("20.00").toMoney(usd),
                            BigDecimal("30.00").toMoney(usd),
                        )

                    list.sum().amount shouldBe BigDecimal("60.00").toMoney(usd).amount
                }

                it("should handle single element list") {
                    val list = listOf(BigDecimal("42.00").toMoney(usd))

                    list.sum().amount shouldBe BigDecimal("42.00").toMoney(usd).amount
                }
            }

            context("when the list is empty") {
                it("should throw IllegalArgumentException") {
                    val emptyList = emptyList<Money>()

                    val exception =
                        shouldThrow<IllegalArgumentException> {
                            emptyList.sum()
                        }

                    exception.message shouldBe "Cannot sum an empty list"
                }
            }

            context("when the list has different currencies") {
                it("should throw IllegalArgumentException") {
                    val list =
                        listOf(
                            BigDecimal("10.00").toMoney(usd),
                            BigDecimal("20.00").toMoney(eur),
                        )

                    val exception =
                        shouldThrow<IllegalArgumentException> {
                            list.sum()
                        }

                    exception.message shouldBe "Not possible to calculate sum value for two or more currencies"
                }
            }
        }
    })
