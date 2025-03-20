package com.archisacademy.parking.services.abstracts;

import com.archisacademy.parking.dtos.request.UserAuthRequest;
import com.archisacademy.parking.dtos.request.UserLoginRequest;
import com.archisacademy.parking.dtos.response.UserAuthResponse;
import feign.Response;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AuthService {
    ResponseEntity<UserAuthResponse>  registerUser(UserAuthRequest userAuthRequest);
    ResponseEntity<Map<String, Object>> loginUser(UserLoginRequest userLoginRequest);
}
