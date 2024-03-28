package com.ggbackendassignment.eventmanagementsystem.controller;

import com.ggbackendassignment.eventmanagementsystem.dto.EventInsertRequest;
import com.ggbackendassignment.eventmanagementsystem.dto.EventInsertResponse;
import com.ggbackendassignment.eventmanagementsystem.dto.UserEventRequest;
import com.ggbackendassignment.eventmanagementsystem.dto.UserEventResponse;
import com.ggbackendassignment.eventmanagementsystem.entity.Event;
import com.ggbackendassignment.eventmanagementsystem.exception.RecordNotFoundException;
import com.ggbackendassignment.eventmanagementsystem.service.EventService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/events")
public class EventController extends BaseController {

    @Autowired
    private EventService eventService;



    @GetMapping(path = "/all", produces = "application/json")
    public ResponseEntity<?> getAllEvents(@RequestHeader Map<String, String> headers) {

        try {
            List<Event> response = eventService.getAllEvents();
            if (response.isEmpty()) {
                throw new RecordNotFoundException("No records present");
            }
            return new ResponseEntity<>(new Gson().toJson(response), HttpStatus.OK);

        } catch (Exception e) {
            return handleAppExceptions(e);
        }

    }


    @PostMapping(value = "/event", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> createEvent(@RequestHeader Map<String, String> headers, @RequestBody String requestBody) {

        try {
            EventInsertRequest event = new Gson().fromJson(requestBody, EventInsertRequest.class);
            validateInputPayload(event);
            EventInsertResponse response = eventService.saveEvent(event);

            return new ResponseEntity<>(new Gson().toJson(response), HttpStatus.CREATED);
        } catch (Exception e) {
            return handleAppExceptions(e);
        }

    }



    @GetMapping(path = "/find", produces = "application/json")
    public ResponseEntity<?> getEventsForUser(@RequestHeader Map<String, String> headers, @Validated @RequestBody String request) {

        try {

            UserEventRequest eventRequest = new Gson().fromJson(request, UserEventRequest.class);
            validateInputPayload(eventRequest);
            UserEventResponse response = eventService.getEventsBasedUser(eventRequest);

            return new ResponseEntity<>(new Gson().toJson(response), HttpStatus.OK);

        } catch (Exception e) {
            return handleAppExceptions(e);
        }

    }

}
