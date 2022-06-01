package com.example.dolorcurrencyexchangeratetogif.api

import com.example.dolorcurrencyexchangeratetogif.payload.ExchangeRatesResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "open-exchange-rates", url = "\${api.open-exchange-rates.url.base}")
interface OpenExchangeRatesApi {
    @GetMapping("\${api.open-exchange-rates.url.latest}")
    fun getLatest(): ExchangeRatesResponse

    @GetMapping("\${api.open-exchange-rates.url.by-date}")
    fun getByDate(@RequestParam date: String): ExchangeRatesResponse
}