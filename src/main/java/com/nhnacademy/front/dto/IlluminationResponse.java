package com.nhnacademy.front.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * 조도 일별 조회 measurement class
 *
 * @author parksangwon
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IlluminationResponse {

    private Instant time;
    private Integer maxIllumination;
    private Integer minIllumination;

}





