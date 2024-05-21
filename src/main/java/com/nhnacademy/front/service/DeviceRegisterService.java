package com.nhnacademy.front.service;

import com.nhnacademy.front.dto.rule.AiModeDto;
import com.nhnacademy.front.dto.rule.CustomModeDto;
import com.nhnacademy.front.dto.rule.RuleDto;

public interface DeviceRegisterService {

    RuleDto parseDeviceRegisterInfo(String deviceRegisterInfo);

    AiModeDto extractAiModeDto(Object aiModeDtoData);

    CustomModeDto extractCustomModeDto(Object customModeDtoData);
}
