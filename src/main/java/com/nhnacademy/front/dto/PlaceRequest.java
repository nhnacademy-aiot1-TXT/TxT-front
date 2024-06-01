package com.nhnacademy.front.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

/**
 * 장소 요청을 처리하기 위한 dto 클래스
 *
 * @author parksangwon
 * @version 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceRequest {
    private String placeName;
    private LocalTime cycle;
}
