package pila.money

sealed class MoneyException(
    message: String,
) : Exception(message) {
    class MonetaryOperationSplitException(
        message: String,
    ) : MoneyException(message)
}
