package com.ggbackendassignment.eventmanagementsystem.dto;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEventDetails {

    @SerializedName("event_name")
    private String eventName;

    @SerializedName("city_name")
    private String cityName;

    @SerializedName("date")
    private String date;

    @SerializedName("weather")
    private String weather;

    @SerializedName("distance_km")
    private Double distanceKM;

}
