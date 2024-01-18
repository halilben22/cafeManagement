package com.halildev.cafeManagement.serviceImpl;

import com.halildev.cafeManagement.constants.CafeConstants;
import com.halildev.cafeManagement.service.UserService;
import com.halildev.cafeManagement.utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;


@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {

        log.info("Inside signup {}", requestMap);
       if (validateSignUpMap(requestMap)){


       }


       else {
           return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
       }
    }


    private boolean validateSignUpMap(Map<String, String> requestMap) {

        return requestMap.containsKey("name") &&
                requestMap.containsKey("contactNumber") &&
                requestMap.containsKey("email") &&
                requestMap.containsKey("password");
    }
}
