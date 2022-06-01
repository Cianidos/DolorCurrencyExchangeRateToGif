package com.example.dolorcurrencyexchangeratetogif.payload

import com.fasterxml.jackson.annotation.JsonProperty

data class ExchangeRatesResponse(
    val disclaimer: String,
    val license: String,
    val timestamp: Long,
    val base: String,
    val rates: Map<String, Float>
)