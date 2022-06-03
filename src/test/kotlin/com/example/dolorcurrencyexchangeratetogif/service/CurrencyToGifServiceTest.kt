package com.example.dolorcurrencyexchangeratetogif.service

import com.example.dolorcurrencyexchangeratetogif.api.GiphyApi
import com.example.dolorcurrencyexchangeratetogif.api.OpenExchangeRatesApi
import com.example.dolorcurrencyexchangeratetogif.exceptions.ThirdPartyServiceNotAvailableException
import com.example.dolorcurrencyexchangeratetogif.exceptions.UnsupportedCurrencyException
import com.example.dolorcurrencyexchangeratetogif.payload.ExchangeRatesResponse
import com.example.dolorcurrencyexchangeratetogif.payload.GifResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrowsExactly
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.anyString
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean


@SpringBootTest
internal class CurrencyToGifServiceTest(
) {
    @MockBean
    lateinit var giphyApi: GiphyApi

    @MockBean
    lateinit var openExchangeRatesApi: OpenExchangeRatesApi

    @Autowired
    lateinit var currencyToGifService: CurrencyToGifService


    @Test
    fun getGifBadCurrencyBoth() {
        given(openExchangeRatesApi.getLatest()).willReturn(
            ExchangeRatesResponse("", "", 0, "", mapOf("RUB" to 10f, "SOM" to 1f))
        )
        given(openExchangeRatesApi.getByDate(anyString())).willReturn(
            ExchangeRatesResponse("", "", 0, "", mapOf("RUB" to 11f, "DOM" to 1f))
        )

        assertThrowsExactly(UnsupportedCurrencyException::class.java) {
            currencyToGifService.getGif("Hello")
        }
    }

    @Test
    fun getGifBadCurrencyLatest() {
        given(openExchangeRatesApi.getLatest()).willReturn(
            ExchangeRatesResponse("", "", 0, "", mapOf("RUB" to 10f, "SOM" to 1f))
        )
        given(openExchangeRatesApi.getByDate(anyString())).willReturn(
            ExchangeRatesResponse("", "", 0, "", mapOf("RUB" to 11f, "DOM" to 1f))
        )

        assertThrowsExactly(UnsupportedCurrencyException::class.java) {
            currencyToGifService.getGif("DOM")
        }
    }

    @Test
    fun getGifBadCurrencyByDate() {
        given(openExchangeRatesApi.getLatest()).willReturn(
            ExchangeRatesResponse("", "", 0, "", mapOf("RUB" to 10f, "SOM" to 1f))
        )
        given(openExchangeRatesApi.getByDate(anyString())).willReturn(
            ExchangeRatesResponse("", "", 0, "", mapOf("RUB" to 11f, "DOM" to 1f))
        )

        assertThrowsExactly(UnsupportedCurrencyException::class.java) {
            currencyToGifService.getGif("SOM")
        }
    }

    @Test
    fun getGifServiceUnavailableOpenExchange() {
        given(openExchangeRatesApi.getLatest()).willThrow(mock(feign.FeignException::class.java))

        given(openExchangeRatesApi.getByDate(anyString())).willReturn(
            ExchangeRatesResponse("", "", 0, "", mapOf("RUB" to 11f, "DOM" to 1f))
        )

        assertThrowsExactly(ThirdPartyServiceNotAvailableException::class.java) {
            currencyToGifService.getGif("rub")
        }?.message?.let {
            assert(it.contains("OpenExchangeRates"))
            assert(it.contains("not available"))
        }
    }

    @Test
    fun getGifServiceUnavailableGiphyBroke() {
        given(openExchangeRatesApi.getLatest()).willReturn(
            ExchangeRatesResponse("", "", 0, "", mapOf("RUB" to 10f, "SOM" to 1f))
        )
        given(openExchangeRatesApi.getByDate(anyString())).willReturn(
            ExchangeRatesResponse("", "", 0, "", mapOf("RUB" to 11f, "DOM" to 1f))
        )
        given(giphyApi.findRandomBroke()).willThrow(mock(feign.FeignException::class.java))

        assertThrowsExactly(ThirdPartyServiceNotAvailableException::class.java) {
            currencyToGifService.getGif("rub")
        }?.message?.let {
            assert(it.contains("Giphy"))
            assert(it.contains("not available"))
        }
    }
    @Test
    fun getGifServiceUnavailableGiphyRich() {
        given(openExchangeRatesApi.getLatest()).willReturn(
            ExchangeRatesResponse("", "", 0, "", mapOf("RUB" to 12f, "SOM" to 1f))
        )
        given(openExchangeRatesApi.getByDate(anyString())).willReturn(
            ExchangeRatesResponse("", "", 0, "", mapOf("RUB" to 11f, "DOM" to 1f))
        )
        given(giphyApi.findRandomRich()).willThrow(mock(feign.FeignException::class.java))

        assertThrowsExactly(ThirdPartyServiceNotAvailableException::class.java) {
            currencyToGifService.getGif("rub")
        }?.message?.let {
            assert(it.contains("Giphy"))
            assert(it.contains("not available"))
        }
    }

    @Test
    fun getGifServiceHealth() {
        given(openExchangeRatesApi.getLatest()).willReturn(
            ExchangeRatesResponse("", "", 0, "", mapOf("RUB" to 12f, "SOM" to 1f))
        )
        given(openExchangeRatesApi.getByDate(anyString())).willReturn(
            ExchangeRatesResponse("", "", 0, "", mapOf("RUB" to 11f, "DOM" to 1f))
        )
        val gifResponseRich: GifResponse = GifResponse(data = mapOf("hello" to "world"), meta = mapOf("one" to "two"))
        val gifResponseBroke: GifResponse = GifResponse(data = mapOf("Hello" to "World"), meta = mapOf("One" to "Two"))
        given(giphyApi.findRandomRich()).willReturn(gifResponseRich)
        given(giphyApi.findRandomBroke()).willReturn(gifResponseBroke)

        assertEquals(currencyToGifService.getGif("rub"), gifResponseRich)

        given(openExchangeRatesApi.getLatest()).willReturn(
            ExchangeRatesResponse("", "", 0, "", mapOf("RUB" to 12f, "SOM" to 1f))
        )
        given(openExchangeRatesApi.getByDate(anyString())).willReturn(
            ExchangeRatesResponse("", "", 0, "", mapOf("RUB" to 13f, "DOM" to 1f))
        )

        assertEquals(currencyToGifService.getGif("rub"), gifResponseBroke)
    }
}