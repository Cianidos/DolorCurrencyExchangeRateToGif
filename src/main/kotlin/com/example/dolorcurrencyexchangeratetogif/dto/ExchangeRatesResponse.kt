package com.example.dolorcurrencyexchangeratetogif.dto

data class ExchangeRatesResponse(
    val disclaimer: String,
    val license: String,
    val timestamp: Long,
    val base: String,
    val rates: Map<String, Float>
)