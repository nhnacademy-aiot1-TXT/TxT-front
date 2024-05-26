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
public class RuleRegisterController {

    private final RuleRegisterAdaptor ruleRegisterAdaptor;

    /**
     * 장치 등록 정보를 전송하는 요청을 처리하는 매서드.
     *
     * @param request HTTP 요청 객체
     * @param deviceRegisterInfo 장치 등록 정보(JSON 형식)
     * @return HTTP 응답 엔터티 (장치 등록 성공 또는 실패 메시지)
     * @throws DeviceRegisterException 장치 등록에 실패한 경우 발생하는 예외
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
