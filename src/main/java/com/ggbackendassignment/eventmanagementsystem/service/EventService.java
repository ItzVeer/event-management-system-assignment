package com.ggbackendassignment.eventmanagementsystem.service;

import com.ggbackendassignment.eventmanagementsystem.dto.*;
import com.ggbackendassignment.eventmanagementsystem.entity.Event;
import com.ggbackendassignment.eventmanagementsystem.exception.ApiCallFailedException;
import com.ggbackendassignment.eventmanagementsystem.repository.EventRepository;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired private RestTemplate restTemplate;

    @Value("${base.url}")
    private String baseUrl;

    @Value("${weather-api.code}")
    private String weatherApiCode;

    @Value("${distance-api.code}")
    private String distanceApiCode;

    public EventInsertResponse saveEvent(EventInsertRequest event) {

        Event eventEntity = new Event();

        eventEntity.setEventName(event.getEventName());
        eventEntity.setCityName(event.getCityName());

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        eventEntity.setEventDate(LocalDate.parse(event.getEventDate(), dateFormatter));

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        eventEntity.setEventTime(LocalTime.parse(event.getEventTime(), timeFormatter));

        eventEntity.setLatitude(event.getLatitude());
        eventEntity.setLongitude(event.getLongitude());
        Event query = eventRepository.save(eventEntity);

        return getResponse(query);

    }

    private EventInsertResponse getResponse(Event query) {
        EventInsertResponse response = new EventInsertResponse();
        if(query != null) {
            response.setId(query.getId());
            response.setEventName(query.getEventName());
            response.setCityName(query.getCityName());
            response.setEventDate(query.getEventDate().toString());
            response.setEventTime(query.getEventTime().toString());
            response.setLatitude(query.getLatitude());
            response.setLongitude(query.getLongitude());
        }
        return response;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public UserEventResponse getEventsBasedUser(UserEventRequest eventRequest) {

        int pageSize = 10;
        int daysToAdd = 14;
        UserEventResponse response = new UserEventResponse();
        List<UserEventDetails> eventDetails = new ArrayList<>(pageSize);

        Pageable pageable = PageRequest.of(0, pageSize);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(eventRequest.getDate(), dateFormatter);
        LocalDate endDate = startDate.plusDays(daysToAdd);

        List<Event> eventEntityList= eventRepository.findByEventDateBetweenOrderByEventDate(startDate, endDate, pageable);

        for(Event event : eventEntityList){
            UserEventDetails userEventDetail = new UserEventDetails();
            userEventDetail.setEventName(event.getEventName());
            userEventDetail.setCityName(event.getCityName());
            userEventDetail.setDate(event.getEventDate().toString());

            userEventDetail.setDistanceKM(Double.valueOf(getDistanceViaGetApiCall(eventRequest, event)));
            userEventDetail.setWeather(getWeatherViaApiCall(event));

            eventDetails.add(userEventDetail);
        }

        response.setEvents(eventDetails);
        return response;
    }

    private String getWeatherViaApiCall(Event event) {
        WeatherApiResponse apiResponse = new WeatherApiResponse();
        StringBuilder apiUrl = new StringBuilder();

        apiUrl.append(baseUrl);
        apiUrl.append("/api/Weather");

        apiUrl.append("?code=");
        apiUrl.append(weatherApiCode);

        apiUrl.append("&city=");
        apiUrl.append(event.getCityName());

        apiUrl.append("&date=");
        apiUrl.append(event.getEventDate());

        ResponseEntity<String> httpResponse = restTemplate.getForEntity(apiUrl.toString(), String.class);

        if (httpResponse.getStatusCode().is2xxSuccessful()) {
            apiResponse = new Gson().fromJson(httpResponse.getBody(), WeatherApiResponse.class);
        } else {
            String message = "Error occurred for Api call: "+apiUrl +" "+ httpResponse.getStatusCode();
            log.error(message);
            throw new ApiCallFailedException(message);
        }

        return apiResponse.getWeather();
    }


    private String getDistanceViaGetApiCall(UserEventRequest eventRequest, Event event) {
        DistanceApiResponse apiResponse = new DistanceApiResponse();
        StringBuilder apiUrl = new StringBuilder();

        apiUrl.append(baseUrl);
        apiUrl.append("/api/Distance?");

        apiUrl.append("code=");
        apiUrl.append(distanceApiCode);

        apiUrl.append("&latitude1=");
        apiUrl.append(eventRequest.getLatitude());

        apiUrl.append("&longitude1=");
        apiUrl.append(eventRequest.getLongitude());

        apiUrl.append("&latitude2=");
        apiUrl.append(event.getLatitude());

        apiUrl.append("&longitude2=");
        apiUrl.append(event.getLongitude());

        ResponseEntity<String> httpResponse = restTemplate.getForEntity(apiUrl.toString(), String.class);

        if (httpResponse.getStatusCode().is2xxSuccessful()) {
            apiResponse = new Gson().fromJson(httpResponse.getBody(), DistanceApiResponse.class);
        } else {
            String message = "Error occurred for Api call: "+apiUrl +" "+ httpResponse.getStatusCode();
            log.error(message);
            throw new ApiCallFailedException(message);
        }

        return apiResponse.getDistance();
    }

}
