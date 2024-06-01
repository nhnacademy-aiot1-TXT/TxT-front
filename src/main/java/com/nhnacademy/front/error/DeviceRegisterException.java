package com.nhnacademy.front.error;

/**
 * 규칙 등록에서 발생하는 예외를 처리하기 위한 예외 클래스
 *
 * @author parksangwon
 * @version 1.0.0
 */
public class DeviceRegisterException extends RuntimeException {

    /**
     * message를 가지는 생성자
     *
     * @param message the message
     */
    public DeviceRegisterException(String message) {
        super(message);
    }
}
