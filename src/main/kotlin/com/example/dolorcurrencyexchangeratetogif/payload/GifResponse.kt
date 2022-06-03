package com.example.dolorcurrencyexchangeratetogif.payload

import com.fasterxml.jackson.annotation.JsonIgnoreProperties


@JsonIgnoreProperties(ignoreUnknown = true)
data class GifResponse(
    val data: Map<*, *>,
    val meta: Map<*, *>,
)

