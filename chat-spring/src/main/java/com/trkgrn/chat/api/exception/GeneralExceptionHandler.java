package com.trkgrn.chat.api.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GeneralExceptionHandler.class);

    @ExceptionHandler(value = { NotFoundExc.class })
    public ResponseEntity<Object> handleNotFoundException(NotFoundExc ex) {

        logger.error("NotFoundException Bulundu: ",ex.getMessage());

        return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = { NullPointerExc.class })
    public ResponseEntity<Object> handleNullPointerException(NullPointerExc ex) {

        logger.error("NullPointerException Bulundu: ",ex.getMessage());

        return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = { SQLExc.class })
    public ResponseEntity<Object> handleSQLException(SQLExc ex) {

        logger.error("SQLException Bulundu: ",ex.getMessage());

        return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = { ExpiredJwtExc.class })
    public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtExc ex) {

        logger.error("ExpiredJwtExc Bulundu: ",ex.getMessage());

        return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.UNAUTHORIZED);

    }

}