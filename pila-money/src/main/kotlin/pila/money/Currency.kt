package pila.money

data class Currency(
    val code: String,
    val displayName: String,
    val symbol: String,
) {
    init {
        require(
            code.matches(Regex("[A-Z]{3}")),
        ) { "Currency code must be 3 uppercase letters" }

        require(
            displayName
                .isBlank()
                .not(),
        ) { "Display name must not be empty" }

        require(
            symbol
                .isBlank()
                .not(),
        ) { "Symbol must not be empty" }
    }
}
