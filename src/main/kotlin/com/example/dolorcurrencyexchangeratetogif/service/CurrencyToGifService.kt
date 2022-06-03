package com.example.dolorcurrencyexchangeratetogif.service

import com.example.dolorcurrencyexchangeratetogif.api.GiphyApi
import com.example.dolorcurrencyexchangeratetogif.api.OpenExchangeRatesApi
import com.example.dolorcurrencyexchangeratetogif.exceptions.ThirdPartyServiceNotAvailableException
import com.example.dolorcurrencyexchangeratetogif.exceptions.UnsupportedCurrencyException
import com.example.dolorcurrencyexchangeratetogif.payload.ExchangeRatesResponse
import com.example.dolorcurrencyexchangeratetogif.payload.GifResponse
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class CurrencyToGifService(
    private val giphyApi: GiphyApi,
    private val openExchangeRatesApi: OpenExchangeRatesApi
) {

    private val yesterdayDateString: String = LocalDate.now().plusDays(-1).format(DateTimeFormatter.ISO_LOCAL_DATE)

    private fun isExchangeRateRise(currency: String): Boolean {
        try {
            val latest: ExchangeRatesResponse = openExchangeRatesApi.getLatest()
            val yesterday: ExchangeRatesResponse = openExchangeRatesApi.getByDate(yesterdayDateString)

            if (!latest.rates.containsKey(currency))
                throw UnsupportedCurrencyException("No \"$currency\" in latest exchange rates")
            if (!yesterday.rates.containsKey(currency))
                throw UnsupportedCurrencyException("No \"$currency\" in yesterday exchange rates")

            return latest.rates[currency]!! > yesterday.rates[currency]!!
        } catch (e: feign.FeignException) {
            throw ThirdPartyServiceNotAvailableException("OpenExchangeRates not available")
        }
    }

    fun getGif(currency: String): GifResponse {
        try {
            return if (isExchangeRateRise(currency.uppercase())) giphyApi.findRandomRich() else giphyApi.findRandomBroke()
        } catch (e: feign.FeignException) {
            throw ThirdPartyServiceNotAvailableException("Giphy not available")
        }
    }
}