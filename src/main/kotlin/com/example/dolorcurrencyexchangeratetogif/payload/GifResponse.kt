package com.example.dolorcurrencyexchangeratetogif.payload

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


@JsonIgnoreProperties(ignoreUnknown = true)
data class GifResponse(
    val data: GifObject,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class GifObject(
    val type: String,
    val id: String,
    val url: String,
)
