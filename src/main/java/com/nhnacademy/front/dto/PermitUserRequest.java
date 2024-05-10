package com.nhnacademy.front.dto;

import lombok.*;


/**
 * The type Permit user request.
 *
 * @author parksangwon
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PermitUserRequest {

    private String id;

    public void setId(String id) {
        this.id = id;
    }
}
