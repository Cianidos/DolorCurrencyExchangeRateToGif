package com.example.dolorcurrencyexchangeratetogif.controller

import com.example.dolorcurrencyexchangeratetogif.exceptions.UnsupportedCurrencyException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice

@ControllerAdvice
class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(UnsupportedCurrencyException::class)
    fun unsupportedCurrency(exception: UnsupportedCurrencyException): ResponseEntity<String> {
        return ResponseEntity<String>(exception.message, HttpStatus.UNPROCESSABLE_ENTITY)
    }
}