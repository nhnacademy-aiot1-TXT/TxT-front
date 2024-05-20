package com.nhnacademy.front.interceptor;


import com.nhnacademy.front.adaptor.DeviceSettingAdapter;
import com.nhnacademy.front.adaptor.UserAdapter;
import com.nhnacademy.front.dto.NotificationResponse.NotificationResponse;
import com.nhnacademy.front.utils.AccessTokenUtil;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;


/**
 * Model에 알림 데이터를 추가해주는 Interceptor입니다.
 */


@RequiredArgsConstructor
public class AlarmInterceptor implements HandlerInterceptor {


    private final DeviceSettingAdapter deviceSettingAdapter;



    /**
     * common-api의 notification을 조회하여, model의 속성에 추가해줍니다.
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     */

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);

        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);

        if(modelAndView == null){
            return ;
        }

        ModelMap model = modelAndView.getModelMap();
        //login logout 을 제외하고, 모든 페이지에 넣는다.
        if (request.getRequestURI().equals("/logout")) {
            return ;
        } else if (request.getRequestURI().equals("/login")) {
            return ;
        }
        else{
//            List<NotificationResponse> notificationResponseList = deviceSettingAdapter.getNotifications(accessToken);
//            model.addAttribute("alarmList", notificationResponseList);
        }

    }
}
