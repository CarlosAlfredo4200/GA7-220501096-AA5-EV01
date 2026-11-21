package com.sena.authapisena.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Maneja de manera centralizada los errores
 * de validación generados por la API.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Procesa los errores encontrados en los DTO de entrada.
     *
     * @param exception excepción de validación.
     * @return respuesta HTTP 400 con los campos que presentan errores.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarValidaciones(
            MethodArgumentNotValidException exception
    ) {

        Map<String, String> errores = new LinkedHashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errores.put(
                        error.getField(),
                        error.getDefaultMessage()
                ));

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("mensaje", "Error de validación.");
        respuesta.put("errores", errores);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(respuesta);
    }
}
