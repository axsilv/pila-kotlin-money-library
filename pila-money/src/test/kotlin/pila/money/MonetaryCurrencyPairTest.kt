package pila.money

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class MonetaryCurrencyPairTest :
    DescribeSpec({

        val usd = MonetaryCurrency("USD", "US Dollar", 2, "$")
        val eur = MonetaryCurrency("EUR", "Euro", 2, "€")
        val brl = MonetaryCurrency("BRL", "Real", 2, "R$")

        val usdEur = MonetaryCurrencyPair(usd, eur)
        val eurUsd = MonetaryCurrencyPair(eur, usd)

        describe("MonetaryCurrencyPair") {

            it("should invert the pair") {
                usdEur.invert() shouldBe eurUsd
            }

            it("should return correct code") {
                usdEur.code() shouldBe "USD/EUR"
                eurUsd.code() shouldBe "EUR/USD"
            }

            it("should detect contained currencies") {
                usdEur.contains(usd) shouldBe true
                usdEur.contains(eur) shouldBe true
                usdEur.contains(brl) shouldBe false
            }

            it("should compare equality with and without order") {
                usdEur.isSameAs(eurUsd, ignoreOrder = true) shouldBe true
                usdEur.isSameAs(eurUsd, ignoreOrder = false) shouldBe false
                usdEur.isSameAs(usdEur, ignoreOrder = false) shouldBe true
            }

            it("should match base and counter codes correctly") {
                usdEur.matches("USD", "EUR") shouldBe true
                usdEur.matches("EUR", "USD") shouldBe false
                usdEur.matches("USD", "BRL") shouldBe false
            }
        }
    })
