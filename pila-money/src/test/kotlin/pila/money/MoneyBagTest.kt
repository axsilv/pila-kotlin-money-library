package pila.money

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContainExactly
import pila.money.Money.Companion.toMoney
import java.math.BigDecimal

class MoneyBagTest :
    DescribeSpec({

        val usd = MonetaryCurrency("USD", "US Dollar", 2, "$")
        val eur = MonetaryCurrency("EUR", "Euro", 2, "€")

        describe("MoneyBag") {

            it("should store and retrieve money by currency") {
                val bag = MoneyBag()
                val m1 = BigDecimal("10.00") toMoney usd
                val m2 = BigDecimal("20.00") toMoney usd
                val m3 = BigDecimal("5.00") toMoney eur

                bag.put(m1)
                bag.put(m2)
                bag.put(m3)

                bag.getAll(usd) shouldContainExactly listOf(m1, m2)
                bag.getAll(eur) shouldContainExactly listOf(m3)
            }

            it("should return all monies regardless of currency") {
                val bag = MoneyBag()
                val m1 = BigDecimal("10.00") toMoney usd
                val m2 = BigDecimal("5.00") toMoney eur

                bag.put(m1)
                bag.put(m2)

                bag.getAll() shouldContainExactly listOf(m1, m2)
            }

            it("should remove a money correctly") {
                val bag = MoneyBag()
                val m1 = BigDecimal("10.00") toMoney usd
                val m2 = BigDecimal("20.00") toMoney usd

                bag.put(m1)
                bag.put(m2)

                val removed = bag.remove(m1)
                removed.shouldBeTrue()

                bag.getAll(usd) shouldContainExactly listOf(m2)
            }

            it("should return false when removing a money not in the bag") {
                val bag = MoneyBag()
                val m1 = BigDecimal("10.00") toMoney usd

                bag.put(m1)

                val fakeMoney = BigDecimal("999.00") toMoney usd
                bag.remove(fakeMoney).shouldBeFalse()
            }

            it("should remove currency key if list becomes empty") {
                val bag = MoneyBag()
                val m1 = BigDecimal("10.00") toMoney usd

                bag.put(m1)
                bag.remove(m1)

                bag.getAll(usd).isEmpty().shouldBeTrue()
            }
        }
    })
