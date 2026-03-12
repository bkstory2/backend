package com.example.vuebackend.model;

import lombok.Data;

@Data
public class StateStatsDto {
    private String airportCode;
    private String routePath;
    private String status;
    private int callCnt;
    private double avgDurationSec;
    private double longitude;
    private double latitude;
}
