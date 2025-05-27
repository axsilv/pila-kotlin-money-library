package pila.money

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class CurrencyTest :
    DescribeSpec({
        describe("Currency") {
            it("Should create a valid Currency") {
                val monetaryCurrency =
                    MonetaryCurrency(
                        code = "USD",
                        displayName = "US Dollar",
                        symbol = "$",
                        scale = 2,
                    )

                monetaryCurrency.code shouldBe "USD"
                monetaryCurrency.displayName shouldBe "US Dollar"
                monetaryCurrency.symbol shouldBe "$"
                monetaryCurrency.scale shouldBe 2
            }

            it("Should default scale to 2 if not provided") {
                val monetaryCurrency =
                    MonetaryCurrency(
                        code = "EUR",
                        displayName = "Euro",
                        symbol = "€",
                    )

                monetaryCurrency.scale shouldBe 2
            }

            it("Should throw if code is not 3 uppercase letters") {
                shouldThrow<IllegalArgumentException> {
                    MonetaryCurrency(
                        code = "usd",
                        displayName = "US Dollar",
                        symbol = "$",
                    )
                }.message shouldBe "Currency code must be 3 uppercase letters"

                shouldThrow<IllegalArgumentException> {
                    MonetaryCurrency(
                        code = "US",
                        displayName = "US Dollar",
                        symbol = "$",
                    )
                }.message shouldBe "Currency code must be 3 uppercase letters"

                shouldThrow<IllegalArgumentException> {
                    MonetaryCurrency(
                        code = "USDE",
                        displayName = "US Dollar",
                        symbol = "$",
                    )
                }.message shouldBe "Currency code must be 3 uppercase letters"
            }

            it("should throw if display name is blank") {
                shouldThrow<IllegalArgumentException> {
                    MonetaryCurrency(
                        code = "USD",
                        displayName = "   ",
                        symbol = "$",
                    )
                }.message shouldBe "Display name must not be empty"
            }

            it("should throw if symbol is blank") {
                shouldThrow<IllegalArgumentException> {
                    MonetaryCurrency(
                        code = "USD",
                        displayName = "US Dollar",
                        symbol = " ",
                    )
                }.message shouldBe "Symbol must not be empty"
            }

            it("should throw if scale is negative") {
                shouldThrow<IllegalArgumentException> {
                    MonetaryCurrency(
                        code = "USD",
                        displayName = "US Dollar",
                        symbol = "$",
                        scale = -1,
                    )
                }.message shouldBe "Scale must be equal or greater than 0"
            }
        }
    })
