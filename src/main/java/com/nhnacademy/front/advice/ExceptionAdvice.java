package com.nhnacademy.front.advice;

import com.nhnacademy.front.adaptor.UserAdaptor;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(basePackages = "com.nhnacademy.front")
@RequiredArgsConstructor
public class ExceptionAdvice {
    private final UserAdaptor userAdaptor;
    @ExceptionHandler(FeignException.class)
    public String feignExceptionHandler(FeignException exception, HttpServletRequest request){
        System.out.println(exception.getMessage());
        if(exception.status() == 400){
            return "redirect:/logout";
        } else if (exception.status() == 401) {
            try {
//                userAdaptor.reissue();
            } catch (FeignException e) {
                return "redirect:/logout";
            }
        }
        return "redirect:" + request.getRequestURI();
    }
}