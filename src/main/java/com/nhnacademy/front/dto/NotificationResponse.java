package com.nhnacademy.front.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Notification 응답 DTO 클래스
 *
 * @author jongsikk
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
    Long roleId;
    String contents;
    LocalDateTime time;
}
