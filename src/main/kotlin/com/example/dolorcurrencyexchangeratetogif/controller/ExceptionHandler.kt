package com.example.dolorcurrencyexchangeratetogif.controller

import com.example.dolorcurrencyexchangeratetogif.exceptions.ThirdPartyServiceNotAvailableException
import com.example.dolorcurrencyexchangeratetogif.exceptions.UnsupportedCurrencyException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(UnsupportedCurrencyException::class)
    fun unsupportedCurrency(exception: UnsupportedCurrencyException): ResponseEntity<String> {
        return ResponseEntity<String>(exception.message, HttpStatus.UNPROCESSABLE_ENTITY)
    }
    @ExceptionHandler(ThirdPartyServiceNotAvailableException::class)
    fun serviceNotAvailable(exception: ThirdPartyServiceNotAvailableException): ResponseEntity<String> {
        return ResponseEntity<String>(exception.message, HttpStatus.SERVICE_UNAVAILABLE)
    }
}