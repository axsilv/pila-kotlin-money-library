> Money library for kotlin

⚠️ Just a learning experiment. Do not use this in real applications.

# 💸 Pila Money

A Kotlin library to represent monetary values with precision, clarity, and safe operations. 

## 📦 Installation

> Clone the repo and install it locally:

```bash
./gradlew publishToMavenLocal
```

## 🏗️ Usage

> ✅ Creating Money instances

You can create monetary values using BigDecimal or String, with or without specifying the currency:

```
kotlin

import pila.money.Money.Companion.toMoney

val money1 = BigDecimal("10.22") toMoney BRL
val money2 = "10.22" toMoney BRL
```

If you don't want to pass the currency every time, set system properties:

```
kotlin

System.setProperty("pila.currency.code", "BRL")
System.setProperty("pila.currency.displayName", "real")
System.setProperty("pila.currency.symbol", "R$")

val money = "10.22".toMoney() // BRL will be used by default
```

## ➕➖ Operations

### Sum
```
kotlin
val total = (BigDecimal("10.22") toMoney BRL) sum (BigDecimal("5.00") toMoney BRL)
// total = R$ 15.22 BRL
```

### Subtraction
```
kotlin
val diff = (BigDecimal("10.24") toMoney BRL) minus (BigDecimal("10.22") toMoney BRL)
// diff = R$ 0.02 BRL
```

### 💬 Display
```
kotlin

val display = (BigDecimal("10.24") toMoney BRL).display()
// "R$ 10.24 BRL"
```

### 🪙 Cents
```
kotlin

val cents = (BigDecimal("1.24") toMoney BRL).toCents()
// 124
```

### 🔪 Split
Split a monetary value equally among n parts. If needed, adjusts the cents so the total remains exact.
```
kotlin

val result = (BigDecimal("1.00") toMoney BRL) split 3

// Returns: [R$ 0.33 BRL, R$ 0.33 BRL, R$ 0.34 BRL]
```

## 🧪 Testing

Tests are written with Kotest. To run them:

```bash
./gradlew test
```

## 🛠️ Roadmap

- Currency conversion
- Multiple configurable currencies
- Support for custom rounding rules and rates
