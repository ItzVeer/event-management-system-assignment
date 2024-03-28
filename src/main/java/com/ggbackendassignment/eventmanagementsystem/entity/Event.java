package com.ggbackendassignment.eventmanagementsystem.entity;

import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "event")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_name", nullable = false, length = 200)
    private String eventName;

    @Column(name = "city_name", nullable = false, length = 200)
    private String cityName;

    @Column(name = "date", nullable = false)
    private LocalDate eventDate;

    @Column(name = "time", nullable = false)
    private LocalTime eventTime;

    @Column(name = "latitude", nullable = false, precision = 14)
    private Double latitude;

    @Column(name = "longitude", nullable = false, precision = 14)
    private Double longitude;

}
