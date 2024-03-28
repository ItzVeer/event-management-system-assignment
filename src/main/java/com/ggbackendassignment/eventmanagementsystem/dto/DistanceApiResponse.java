package com.ggbackendassignment.eventmanagementsystem.dto;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistanceApiResponse {

    @SerializedName("distance")
    private String distance;
}
