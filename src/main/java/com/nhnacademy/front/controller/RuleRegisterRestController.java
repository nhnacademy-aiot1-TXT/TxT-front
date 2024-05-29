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
@RequestMapping("/admin/rule")
@RequiredArgsConstructor
public class RuleRegisterRestController {

    private final RuleRegisterAdaptor ruleRegisterAdaptor;

    @PostMapping("/send-data")
    public ResponseEntity<String> getRuleRegisterInfo(HttpServletRequest request, @RequestBody String ruleRegisterInfo) {
        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);
        try {
            ruleRegisterAdaptor.sendRuleRegisterInfo(accessToken, ruleRegisterInfo);
            return ResponseEntity.ok("Rule registered successfully!");
        } catch (Exception e) {
            throw new DeviceRegisterException("Rule registered failed!");
        }
    }
}
