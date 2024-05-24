package com.nhnacademy.front.controller;

import com.nhnacademy.front.adaptor.RuleRegisterAdaptor;
import com.nhnacademy.front.error.DeviceRegisterException;
import com.nhnacademy.front.utils.AccessTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin/device")
@RequiredArgsConstructor
public class RuleRegisterController {

    private final RuleRegisterAdaptor deviceRegisterAdaptor;

    @PostMapping("/send-data")
    public ResponseEntity<String> getDeviceRegisterInfo(HttpServletRequest request, @RequestBody String deviceRegisterInfo) {
        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);
        try {
            deviceRegisterAdaptor.sendDeviceInfo(accessToken, deviceRegisterInfo);
            return ResponseEntity.ok("Device registered successfully!");
        } catch (Exception e) {
            throw new DeviceRegisterException("Device registered failed!");
        }
    }
}
