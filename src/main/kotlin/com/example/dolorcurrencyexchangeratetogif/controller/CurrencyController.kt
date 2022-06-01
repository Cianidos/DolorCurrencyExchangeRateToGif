package com.example.dolorcurrencyexchangeratetogif.controller

import com.example.dolorcurrencyexchangeratetogif.api.GiphyApi
import com.example.dolorcurrencyexchangeratetogif.api.OpenExchangeRatesApi
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class CurrencyToGifController(
    val giphyApi: GiphyApi,
    val openExchangeRatesApi: OpenExchangeRatesApi
) {
    @GetMapping("/{currency}")
    fun getGif(@PathVariable currency: String): String {
        return openExchangeRatesApi.getByDate("2022-05-30")
        TODO("Not Implemented")
    }
}