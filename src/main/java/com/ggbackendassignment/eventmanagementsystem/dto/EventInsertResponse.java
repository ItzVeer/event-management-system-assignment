package com.ggbackendassignment.eventmanagementsystem.dto;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventInsertResponse {

    @SerializedName("id")
    private Long id;

    @SerializedName("event_name")
    private String eventName;

    @SerializedName("city_name")
    private String cityName;

    @SerializedName("date")
    private String eventDate;

    @SerializedName("time")
    private String eventTime;

    @SerializedName("latitude")
    private Double latitude;

    @SerializedName("longitude")
    private Double longitude;

}
