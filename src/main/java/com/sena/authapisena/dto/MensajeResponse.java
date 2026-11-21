package com.sena.authapisena.dto;

/**
 * Respuesta estándar utilizada por los servicios de autenticación.
 *
 * @param mensaje mensaje enviado al consumidor de la API.
 */
public record MensajeResponse(String mensaje) {
}
