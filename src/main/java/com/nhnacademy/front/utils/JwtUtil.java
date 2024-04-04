package com.nhnacademy.front.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.front.dto.AccessTokenResponse;
import com.nhnacademy.front.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtUtil {
    private final ObjectMapper mapper;

    public Authentication getAuthentication(AccessTokenResponse accessTokenResponse) throws JsonProcessingException {
        String payload = new String(Base64.getDecoder().decode(accessTokenResponse.getAccessToken().split("\\.")[1]));

        Map<String, Object> map = mapper.readValue(payload, Map.class);
        String username = (String) map.get("userId");
        String authority = (String) map.get("authority");
        List<String> authorities = new ArrayList<>();
        authorities.add(authority);

        Collection<? extends GrantedAuthority> grantedAuthorities = authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UserDetails userDetails = new CustomUserDetails(username, "", grantedAuthorities);
        return new UsernamePasswordAuthenticationToken(userDetails, "", grantedAuthorities);
    }
}