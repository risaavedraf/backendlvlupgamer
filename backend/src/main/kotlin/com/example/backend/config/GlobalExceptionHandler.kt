package com.example.backend.config

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.lang.IllegalArgumentException

// Esta clase "escuchará" las excepciones que ocurran en los controllers
@ControllerAdvice
class GlobalExceptionHandler {

    // Se activa cuando @Valid falla (ej. email inválido, contraseña corta)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, String?>> {
        val errors = ex.bindingResult.fieldErrors.associate {
            it.field to it.defaultMessage
        }
        return ResponseEntity
            .badRequest() // 400
            .body(mapOf("error" to "Error de validación", "details" to errors.toString()))
    }

    // Se activa cuando lanzamos IllegalArgumentException (ej. email duplicado, login incorrecto)
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<Map<String, String?>> {
        
        // Si el error es de "duplicado", usamos un 409. Si no, un 400.
        val status = if (ex.message?.contains("ya está en uso") == true || ex.message?.contains("ya está registrado") == true) {
            HttpStatus.CONFLICT // 409
        } else {
            HttpStatus.BAD_REQUEST // 400
        }

        return ResponseEntity
            .status(status)
            .body(mapOf("error" to ex.message))
    }
}