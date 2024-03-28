package com.ggbackendassignment.eventmanagementsystem.dto;

import com.google.gson.annotations.SerializedName;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEventRequest {

    @NotEmpty(message = "'date' cannot be Empty or NULL")
    @SerializedName("date")
    private String date;

    @DecimalMin(value = "-90.0", message = "The minimum latitude is -90 degrees, which corresponds to the South Pole.")
    @DecimalMax(value = "90.0", message = "The maximum latitude is +90 degrees, which corresponds to the North Pole.")
    @SerializedName("latitude")
    private Double latitude;

    @DecimalMin(value = "-180.0", message = "The minimum longitude is -180 degrees, which corresponds to the International Date Line in the Pacific Ocean.")
    @DecimalMax(value = "180.0", message = "The maximum longitude is +180 degrees, which corresponds to the same line, as it wraps around the Earth.")
    @SerializedName("longitude")
    private Double longitude;
}
