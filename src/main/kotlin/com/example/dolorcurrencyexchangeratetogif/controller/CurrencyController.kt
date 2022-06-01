package com.example.dolorcurrencyexchangeratetogif.controller

import com.example.dolorcurrencyexchangeratetogif.payload.GifResponse
import com.example.dolorcurrencyexchangeratetogif.service.CurrencyToGifService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class CurrencyToGifController(
    private val currencyToGifService: CurrencyToGifService
) {
    @GetMapping("/{currency}")
    fun getGif(@PathVariable currency: String): GifResponse {
        return currencyToGifService.getGif(currency)
    }
}