package com.nhnacademy.front.controller;

import com.nhnacademy.front.adaptor.DeviceSettingAdapter;
import com.nhnacademy.front.adaptor.SensorAdapter;
import com.nhnacademy.front.adaptor.UserAdapter;
import com.nhnacademy.front.dto.PermitUserRequest;
import com.nhnacademy.front.dto.PlaceResponse;
import com.nhnacademy.front.dto.SensorResponse;
import com.nhnacademy.front.dto.UserDataResponse;
import com.nhnacademy.front.utils.AccessTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 어드민 권한만 접근할 수 있는 Controller
 */
@RequestMapping("/admin")
@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserAdapter userAdapter;
    private final SensorAdapter sensorAdapter;
    private final DeviceSettingAdapter deviceSettingAdapter;

    @GetMapping
    public String admin() {
        return "redirect:/";
    }

    @GetMapping("/manage")
    public String manage(HttpServletRequest request, Model model,
                         @RequestParam(value = "statusParam", defaultValue = "userList") String statusParam,
                         @RequestParam(value = "page", defaultValue = "0") int page,
                         @RequestParam(value = "size", defaultValue = "5") int size) {
        Page<UserDataResponse> users;
        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);

        switch (statusParam) {
            case "pendingUser":
                users = userAdapter.findSortedUsers(accessToken, 4, page, size);
                model.addAttribute("actionUrl", "/admin/manage/permit");
                break;
            case "deactivateUser":
                users = userAdapter.findSortedUsers(accessToken, 3, page, size);
                model.addAttribute("actionUrl", "/admin/manage/permit");
                break;
            case "adminList":
                users = userAdapter.findSortedUserByRole(accessToken, page, size, 1);
                break;
            default:
                users = userAdapter.findAllUsers(accessToken, page, size);
                model.addAttribute("actionUrl", "/admin/manage/promotion");
                break;
        }

        model.addAttribute("users", users);
        model.addAttribute("statusSet", statusParam);
        model.addAttribute("accessTokenTemp", AccessTokenUtil.findAccessTokenInRequest(request));


        return "manage";
    }



    /**
     * 사용자 허용 요청을 처리하고 지정된 URL로 리디렉션하는 매서드.
     *
     * <p>이 메서드는 "/manage/permit" URL에 대한 POST 요청에 매핑되어 있습니다.
     * 요청에서 선택된 사용자 ID를 가져와 {@code PermitUserRequest} 객체 리스트를 생성한 후,
     * 접근 토큰과 사용자 요청 리스트를 사용하여 {@code userAdapter}의 {@code permitUser} 메서드를 호출합니다.
     * 처리 후, 지정된 URL로 리디렉션합니다.
     *
     * @param request 사용자 ID 및 기타 매개변수가 포함된 HTTP 서블릿 요청
     * @param redirectUrl 처리 후 리디렉션할 URL
     * @return "/admin"으로 시작하는 지정된 URL로 리디렉션하는 문자열
     */

    //유저등록
    @PostMapping("/manage/permit")
    public String permitUser(HttpServletRequest request, @RequestParam("redirectUrl") String redirectUrl) {
        List<PermitUserRequest> permitUserRequests = new ArrayList<>();
        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);
        String[] selectedUserIds = request.getParameterValues("userIds");

        if (selectedUserIds != null) {
            for (String userId : selectedUserIds) {
                PermitUserRequest permitUserRequest = new PermitUserRequest(); // 각 반복마다 새 객체 생성
                permitUserRequest.setId(userId); // 유저 ID 설정
                permitUserRequests.add(permitUserRequest); // 리스트에 추가
            }
            userAdapter.permitUser(accessToken, permitUserRequests); // 사용자 허용 메서드 호출
        }

        return "redirect:" + redirectUrl.substring(redirectUrl.indexOf("/admin"));
    }


    /**
     * 관리 페이지에서 사용자를 승격하는 메서드.
     * 이 메서드는 HTTP 요청에서 선택된 사용자 ID들을 가져와 PermitUserRequest 객체로 변환한 후,
     * userAdapter.promotionUser 메서드를 호출하여 사용자를 승격시킵니다.
     * 이후, 주어진 redirectUrl의 "/admin" 경로로 리다이렉트합니다.
     *
     * @param request       HTTP 요청 객체.
     * @param redirectUrl   리다이렉트할 URL.
     * @return              리다이렉트 URL 문자열.
     *

     */


    @PostMapping("/manage/promotion")
    public String promotionUser(HttpServletRequest request, @RequestParam("redirectUrl") String redirectUrl) {
        List<PermitUserRequest> permitUserRequests = new ArrayList<>();
        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);
        String[] selectedUserIds = request.getParameterValues("userIds");

        if (selectedUserIds != null) {
            for (String userId : selectedUserIds) {
                PermitUserRequest permitUserRequest = new PermitUserRequest();
                permitUserRequest.setId(userId);
                permitUserRequests.add(permitUserRequest);
            }
            userAdapter.promotionUser(accessToken, permitUserRequests);
        }

        return "redirect:" + redirectUrl.substring(redirectUrl.indexOf("/admin"));
    }

    // 상세센서 정보

    /**
     * 상세 센서 정보 페이지를 반환하는 메서드.
     *
     * @param request    HTTP 요청 객체.
     * @param placeCode  장소 코드를 나타내는 문자열 (기본값은 "class_a").
     * @param model      모델 객체.
     * @return           상세 센서 정보 페이지의 뷰 이름.
     *
     */

    @GetMapping("/detail-sensor-info")
    public String profile(HttpServletRequest request,
                          @RequestParam(value = "placeCode", defaultValue = "class_a") String placeCode,
                          Model model) {
        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);
        List<PlaceResponse> placeList = deviceSettingAdapter.getPlaceList(AccessTokenUtil.findAccessTokenInRequest(request));

        for (PlaceResponse p : placeList) {
            if (p.getPlaceCode().equals(placeCode)) {
                model.addAttribute("currentPlace", p);
            }
        }

        model.addAttribute("accessToken", accessToken);
        model.addAttribute("placeList", placeList);

        return "detailedSensor";
    }

    /**
     * 센서 로그 데이터를 반환하는 메서드.
     * 이 메서드는 주어진 조건에 따라 센서 데이터를 가져와 모델에 추가하고,
     * 센서 로그 데이터 페이지를 반환합니다.
     *
     * @param request     HTTP 요청 객체.
     * @param placeCode   장소 코드를 나타내는 문자열 (기본값은 "class_a").
     * @param sensorType  센서 타입을 나타내는 문자열 (기본값은 "temperature").
     * @param period      기간을 나타내는 문자열 (기본값은 "day").
     * @param model       모델 객체.
     * @return            센서 로그 데이터 페이지의 뷰 이름.
     *

     */

    @GetMapping("/detail-sensor-info/log")
    public String sensorLog(HttpServletRequest request,
                            @RequestParam(value = "placeCode", defaultValue = "class_a") String placeCode,
                            @RequestParam(value = "sensorType", defaultValue = "temperature") String sensorType,
                            @RequestParam(value = "period", defaultValue = "day") String period,
                            Model model) {

        String accessToken = AccessTokenUtil.findAccessTokenInRequest(request);
        List<PlaceResponse> placeList = deviceSettingAdapter.getPlaceList(AccessTokenUtil.findAccessTokenInRequest(request));
        List<SensorResponse> sensorData = sensorAdapter.getSensorData(accessToken, placeCode, sensorType, period)
                                                       .stream()
                                                       .map(d -> {
                                                           Instant newTime = d.getTime().minus(9, ChronoUnit.HOURS);
                                                           d.setTime(newTime);
                                                           return d;
                                                       })
                                                       .collect(Collectors.toList());

        for (PlaceResponse p : placeList) {
            if (p.getPlaceCode().equals(placeCode)) {
                model.addAttribute("currentPlace", p);
            }
        }

        model.addAttribute("sensorType", sensorType);
        model.addAttribute("period", period);
        model.addAttribute("placeList", placeList);
        model.addAttribute("sensorDataList", sensorData);


        model.addAttribute("accessTokenTemp", AccessTokenUtil.findAccessTokenInRequest(request));


        return "dataLog";
    }
}
