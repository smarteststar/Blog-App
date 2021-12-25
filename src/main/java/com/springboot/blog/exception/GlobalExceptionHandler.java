package com.springboot.blog.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //validating Response in 1st way with extending ResponseEntityExceptionHandler
    protected ResponseEntity<Object> handleMethodArgumentNotValid
            (MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
               String fieldName=((FieldError)error).getField();
               String message=error.getDefaultMessage();
               errorMap.put(fieldName,message);
                });
        return new ResponseEntity<>(errorMap,HttpStatus.BAD_REQUEST);
    }

    //validating Response in 2nd way without extending ResponseEntityExceptionHandler
   /* @ExceptionHandler(MethodArgumentNotValidException.class)
    public  ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request){
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName=((FieldError)error).getField();
            String message=error.getDefaultMessage();
            errorMap.put(fieldName,message);
        });
        return new ResponseEntity<>(errorMap,HttpStatus.BAD_REQUEST);
    }*/

}
