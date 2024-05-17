package com.nhnacademy.front.dto.rule;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Condition {
    private String mqttUrl;
    private String topic;
    private ComparisonOperator comparisonOperator;
    private float standardValue;
}
