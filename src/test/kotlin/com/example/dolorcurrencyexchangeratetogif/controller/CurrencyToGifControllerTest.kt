package com.example.dolorcurrencyexchangeratetogif.controller

import com.example.dolorcurrencyexchangeratetogif.api.GiphyApi
import com.example.dolorcurrencyexchangeratetogif.api.OpenExchangeRatesApi
import com.example.dolorcurrencyexchangeratetogif.dto.ExchangeRatesResponse
import com.example.dolorcurrencyexchangeratetogif.dto.GifResponse
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test

import org.mockito.BDDMockito.*
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
internal class CurrencyToGifControllerTest {

    @MockBean
    lateinit var giphyApi: GiphyApi
    @MockBean
    lateinit var openExchangeRatesApi: OpenExchangeRatesApi

    @Autowired
    lateinit var mockMvc: MockMvc
    @Autowired
    lateinit var currencyToGifController: CurrencyToGifController

    @Test
    fun getGifHealth() {
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

        mockMvc.get("/rub").andExpect {
            status { isOk() }
        }

        given(openExchangeRatesApi.getLatest()).willReturn(
            ExchangeRatesResponse("", "", 0, "", mapOf("RUB" to 12f, "SOM" to 1f))
        )
        given(openExchangeRatesApi.getByDate(anyString())).willReturn(
            ExchangeRatesResponse("", "", 0, "", mapOf("RUB" to 14f, "DOM" to 1f))
        )

        mockMvc.get("/rub").andExpect {
            status { isOk() }
        }
    }
    @Test
    fun getGifServiceUnavailableOpenExchangeLatest() {
        given(openExchangeRatesApi.getLatest()).willThrow(mock(feign.FeignException::class.java))
        given(openExchangeRatesApi.getByDate(anyString())).willReturn(
            ExchangeRatesResponse("", "", 0, "", mapOf("RUB" to 11f, "DOM" to 1f))
        )
        val gifResponseRich: GifResponse = GifResponse(data = mapOf("hello" to "world"), meta = mapOf("one" to "two"))
        val gifResponseBroke: GifResponse = GifResponse(data = mapOf("Hello" to "World"), meta = mapOf("One" to "Two"))
        given(giphyApi.findRandomRich()).willReturn(gifResponseRich)
        given(giphyApi.findRandomBroke()).willReturn(gifResponseBroke)

        mockMvc.get("/rub").andExpect {
            status { isServiceUnavailable() }
            content { string(Matchers.containsString("OpenExchangeRates")) }
        }
    }

    @Test
    fun getGifServiceUnavailableOpenExchangeByDate() {
        given(openExchangeRatesApi.getLatest()).willReturn(
            ExchangeRatesResponse("", "", 0, "", mapOf("RUB" to 12f, "SOM" to 1f))
        )
        given(openExchangeRatesApi.getByDate(anyString())).willThrow(mock(feign.FeignException::class.java))

        val gifResponseRich: GifResponse = GifResponse(data = mapOf("hello" to "world"), meta = mapOf("one" to "two"))
        val gifResponseBroke: GifResponse = GifResponse(data = mapOf("Hello" to "World"), meta = mapOf("One" to "Two"))
        given(giphyApi.findRandomRich()).willReturn(gifResponseRich)
        given(giphyApi.findRandomBroke()).willReturn(gifResponseBroke)

        mockMvc.get("/rub").andExpect {
            status { isServiceUnavailable() }
            content { string(Matchers.containsString("OpenExchangeRates")) }
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

        val gifResponseRich: GifResponse = GifResponse(data = mapOf("hello" to "world"), meta = mapOf("one" to "two"))
        given(giphyApi.findRandomRich()).willReturn(gifResponseRich)
        given(giphyApi.findRandomBroke()).willThrow(mock(feign.FeignException::class.java))

        mockMvc.get("/rub").andExpect {
            status { isServiceUnavailable() }
            content { string(Matchers.containsString("Giphy")) }
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

        val gifResponseBroke: GifResponse = GifResponse(data = mapOf("Hello" to "World"), meta = mapOf("One" to "Two"))

        given(giphyApi.findRandomRich()).willThrow(mock(feign.FeignException::class.java))
        given(giphyApi.findRandomBroke()).willReturn(gifResponseBroke)

        mockMvc.get("/rub").andExpect {
            status { isServiceUnavailable() }
            content { string(Matchers.containsString("Giphy")) }
        }
    }

    @Test
    fun getGifBadCurrencyBoth() {
        given(openExchangeRatesApi.getLatest()).willReturn(
            ExchangeRatesResponse("", "", 0, "", mapOf("RUB" to 10f, "SOM" to 1f))
        )
        given(openExchangeRatesApi.getByDate(anyString())).willReturn(
            ExchangeRatesResponse("", "", 0, "", mapOf("RUB" to 11f, "DOM" to 1f))
        )

        mockMvc.get("/en").andExpect {
            status { isUnprocessableEntity() }
        }
    }

}