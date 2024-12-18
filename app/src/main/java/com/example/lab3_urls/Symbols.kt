package com.example.lab3_urls

fun getCurrencySymbol(currencyCode: String): String {
    return when (currencyCode.uppercase()) {
        "USD" -> "\u0024"  // Доллар $
        "EUR" -> "\u20AC"  // Евро €
        "GBP" -> "\u00A3"  // Фунт £
        "JPY" -> "\u00A5"  // Йена ¥
        "RUB" -> "\u20BD"  // Рубль ₽
        "INR" -> "\u20B9"  // Рупия ₹
        "CNY" -> "\u5143"  // Юань 元
        "KRW" -> "\u20A9"  // Вона ₩
        "AUD" -> "A" + "\u0024"  // Австралийский доллар $
        "CAD" -> "C" + "\u0024"  // Канадский доллар $
        "CHF" -> "\u20A3" // Швейцарский франк ₣
        else -> currencyCode // Если символа нет, возвращаем сам код
    }
}

