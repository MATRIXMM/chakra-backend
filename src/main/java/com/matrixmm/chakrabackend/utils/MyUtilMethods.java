package com.matrixmm.chakrabackend.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class MyUtilMethods {
    public static LocalDate getNowDate() {
        LocalDate now = LocalDate.now(ZoneId.of("America/Lima"));
        return now;
    }

    public static LocalDateTime getNowDateTime() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Lima"));
        return now;
    }

    public static String getValidationMessage(BindingResult errors){
        String validationMessage = null;
        if (errors.hasErrors()){
            System.out.println("Hay errores de validacion");
            validationMessage = "";
            FieldError fieldError = errors.getFieldErrors().get(0);
            validationMessage += fieldError.getField()+" " +fieldError.getDefaultMessage() +".";
        }
        return validationMessage;

    }
}
