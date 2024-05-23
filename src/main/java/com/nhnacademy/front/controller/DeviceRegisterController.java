package com.nhnacademy.front.controller;

import com.nhnacademy.front.adaptor.DeviceRegisterAdaptor;
import com.nhnacademy.front.utils.AccessTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/device")
@RequiredArgsConstructor
public class DeviceRegisterController {

    private final DeviceRegisterAdaptor deviceRegisterAdaptor;

    @PostMapping("/register")
    public ResponseEntity<String> getDeviceRegisterInfo(HttpServletRequest request, @RequestBody String deviceRegisterInfo) {
        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);
        try {
            deviceRegisterAdaptor.sendDeviceInfo(accessToken, deviceRegisterInfo);
            return ResponseEntity.ok("Device registered successfully!");
        } catch (Exception e) {
//            return ResponseEntity.status(400).body("Device registration failed: " + e.getMessage());
            throw new RuntimeException(e);//내가 만든거
        }
    }
}
