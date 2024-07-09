package com.nhnacademy.front.adaptor;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 규칙 등록 API 요청을 보내기 위한 adapter 클래스
 */
@FeignClient(value = "rule-engine", path = "/api/rule")
public interface RuleRegisterAdaptor {

    /**
     * 규칙을 등록하기 위한 메서드
     *
     * @param accessToken      the access token
     * @param ruleRegisterInfo the rule register info
     */
    @PostMapping(value = "/device/register")
    void sendRuleRegisterInfo(@RequestHeader("Authorization") String accessToken, @RequestBody String ruleRegisterInfo);
}
