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

/**
 * 규칙 등록 API 요청을 처리하기 위한 클래스
 *
 * @author parksangwon
 * @version 1.0.0
 */
@RestController
@RequestMapping("/admin/rule")
@RequiredArgsConstructor
public class RuleRegisterRestController {
    private final RuleRegisterAdaptor ruleRegisterAdaptor;

    /**
     * 규칙을 등록하는 메서드
     *
     * @param request          the request
     * @param ruleRegisterInfo the rule register info
     * @return the rule register info
     */
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
