package com.nhnacademy.front.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

/**
 * 장소 응답을 처리하기 위한 dto 클래스
 *
 * @author parksangwon
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceResponse {
    private Long placeId;
    private String placeName;
    private String placeCode;
    private LocalTime cycle;
}
