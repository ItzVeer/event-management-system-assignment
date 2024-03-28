package com.ggbackendassignment.eventmanagementsystem.dto;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {


    @SerializedName("timestamp")
    private String timestamp;

    @SerializedName("message")
    private String message;

    @SerializedName("details")
    private List<String> details;
}
