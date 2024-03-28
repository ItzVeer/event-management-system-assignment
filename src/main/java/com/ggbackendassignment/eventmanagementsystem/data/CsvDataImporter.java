package com.ggbackendassignment.eventmanagementsystem.data;

import com.ggbackendassignment.eventmanagementsystem.EventManagementSystemApplication;
import com.ggbackendassignment.eventmanagementsystem.entity.Event;
import com.ggbackendassignment.eventmanagementsystem.repository.EventRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Component
public class CsvDataImporter {

    @Autowired
    private EventRepository eventRepository;

    @Value("${data-set.url}")
    private String FILE_PATH;

    @PostConstruct
    public void init() throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(FILE_PATH).openStream()));
        String line;
        boolean FIRST_LINE = true;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");

            if (FIRST_LINE) {
                FIRST_LINE = false;
                continue; // Skip header
            }

            if (data.length == 6) {
                Event event = new Event();
                event.setEventName(data[0]);
                event.setCityName(data[1]);

                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                event.setEventDate(LocalDate.parse(data[2], dateFormatter));

                String time = data[3];
                if (time.length() == 7) {
                    time = "0" + time;
                }

                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                event.setEventTime(LocalTime.parse(time, timeFormatter));

                event.setLatitude(Double.parseDouble(data[4]));
                event.setLongitude(Double.parseDouble(data[5]));

                eventRepository.save(event);
            } else {
                log.error("Invalid CSV format for line: " + line);
            }
        }

        reader.close();
    }

}
