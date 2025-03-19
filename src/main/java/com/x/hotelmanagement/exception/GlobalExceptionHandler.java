package com.x.hotelmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice // Tüm uygulama genelinde hata yönetimi sağlar
public class GlobalExceptionHandler {

    /**
     * Genel hata yakalama metodu.
     * @param ex Fırlatılan hata
     * @return ResponseEntity<Map<String, Object>> JSON formatında hata mesajı döndürür.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("error", "Internal Server Error");
        errorResponse.put("message", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Özel olarak RuntimeException'ı yakalar.
     * @param ex RuntimeException
     * @return ResponseEntity<Map<String, Object>> JSON formatında hata mesajı döndürür.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("error", "Bad Request");
        errorResponse.put("message", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
