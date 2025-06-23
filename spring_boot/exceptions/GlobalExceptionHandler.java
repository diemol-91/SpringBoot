package com.diegom.spring_boot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.dao.DataIntegrityViolationException;
import java.sql.SQLException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Maneja específicamente errores de FK en MySQL
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        if (ex.getRootCause() instanceof SQLException) {
            SQLException sqlEx = (SQLException) ex.getRootCause();
            // Código 1452 = violación de FK en MySQL
            if (sqlEx.getErrorCode() == 1452) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Operación no permitida: El registro está referenciado por otros datos. "
                                + "Asegúrese de que: \n"
                                + "1. Los registros referenciados existan\n"
                                + "2. No esté intentando eliminar un registro en uso");
            }
        }

        // Para otros errores de integridad de datos
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Error de integridad de datos: " + ex.getRootCause().getMessage());
    }

    // Puedes agregar más manejadores para otros tipos de excepciones aquí
}