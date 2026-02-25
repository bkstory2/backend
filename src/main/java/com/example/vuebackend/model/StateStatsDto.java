package com.example.vuebackend.model;

import lombok.Data;

@Data
public class StateStatsDto {
    private String apiCallCd;
    private String accsUrlPath;
    private String httpStacdVal;
    private int callCnt;
    private double avgDurationSec;
    private double srcX;
    private double srcY;
    private double destX;
    private double destY;
}
