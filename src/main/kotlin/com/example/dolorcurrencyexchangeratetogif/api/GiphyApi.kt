package com.example.dolorcurrencyexchangeratetogif.api

import com.example.dolorcurrencyexchangeratetogif.payload.GifResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping


@FeignClient(name = "giphy", url = "\${api.giphy.url.base}", )
interface GiphyApi {

    @GetMapping("\${api.giphy.url.rich}")
    fun findRandomRich(): GifResponse

    @GetMapping("\${api.giphy.url.broke")
    fun findRandomBroke(): GifResponse
}
