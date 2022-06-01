package com.example.dolorcurrencyexchangeratetogif

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class DolorCurrencyExchangeRateToGifApplication

fun main(args: Array<String>) {
    runApplication<DolorCurrencyExchangeRateToGifApplication>(*args)
}
