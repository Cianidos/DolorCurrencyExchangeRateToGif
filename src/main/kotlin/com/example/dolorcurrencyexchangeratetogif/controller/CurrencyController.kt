package com.example.dolorcurrencyexchangeratetogif.controller

import com.example.dolorcurrencyexchangeratetogif.api.GiphyApi
import com.example.dolorcurrencyexchangeratetogif.payload.GifResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.Currency

@RestController
class CurrencyToGifController(
    val giphyApi: GiphyApi
) {
    @GetMapping("/{currency}")
    fun getGif(@PathVariable currency: String): GifResponse {
        return giphyApi.findRandomRich()
        TODO("Not Implemented")
    }
}