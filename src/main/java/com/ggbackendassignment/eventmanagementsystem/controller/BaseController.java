package com.ggbackendassignment.eventmanagementsystem.controller;

import com.ggbackendassignment.eventmanagementsystem.dto.ErrorResponse;
import com.ggbackendassignment.eventmanagementsystem.exception.ApiCallFailedException;
import com.ggbackendassignment.eventmanagementsystem.exception.InputPayloadException;
import com.ggbackendassignment.eventmanagementsystem.exception.RecordNotFoundException;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Component
public class BaseController {

    @Autowired private Validator validator;

    public void validateInputPayload(Object vo) throws InputPayloadException {
        ArrayList<String> messages = new ArrayList<>();

        if (vo == null) {
            throw new InputPayloadException("RequestBody cannot be null");
        }

        DirectFieldBindingResult result = new DirectFieldBindingResult(vo, vo.getClass().getName());
        validator.validate(vo, result);
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            Iterator<ObjectError> iterator = errors.iterator();
            ObjectError obj;
            while (iterator.hasNext()) {
                obj = iterator.next();
                messages.add(obj.getDefaultMessage());
            }
            throw new InputPayloadException(String.valueOf(messages));
        }
    }


    public ResponseEntity<?> handleAppExceptions(Exception e){
        log.error(e.getMessage(), e.getLocalizedMessage());

        ErrorResponse response = new ErrorResponse();
        response.setMessage(e.getMessage());
        response.setDetails(Collections.singletonList(e.getLocalizedMessage()));
        response.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        if(e instanceof RecordNotFoundException){
            return new ResponseEntity<>(new Gson().toJson(response), HttpStatus.NOT_FOUND);
        } else if (e instanceof InputPayloadException) {
            return new ResponseEntity<>(new Gson().toJson(response), HttpStatus.NOT_FOUND);
        } else if (e instanceof ApiCallFailedException) {
            return new ResponseEntity<>(new Gson().toJson(response), HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            return new ResponseEntity<>(new Gson().toJson(response), HttpStatus.BAD_REQUEST);
        }

    }

}
