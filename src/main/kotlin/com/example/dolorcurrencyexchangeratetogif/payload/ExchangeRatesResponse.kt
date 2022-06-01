package com.example.dolorcurrencyexchangeratetogif.payload

data class ExchangeRatesResponse(
    val disclaimer: String,
    val license: String,
    val timestamp: Long,
    val base: String,
    val rates: Map<String, Float>
)