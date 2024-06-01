package com.nhnacademy.front.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * 메시지 큐에 값을 등록하기 위한 dto 클래스
 *
 * @author parksangwon
 * @version 1.0.0
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class ValueMessage {
    private String place;
    private boolean value;
}