package com.halildev.cafeManagement.restImpl;

import com.halildev.cafeManagement.rest.UserRest;
import com.halildev.cafeManagement.service.UserService;
import com.halildev.cafeManagement.utils.CafeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static com.halildev.cafeManagement.constants.CafeConstants.SOMETHING_WENT_WRONG;

public class UserRestImpl implements UserRest {



  private final   UserService userService;

    public UserRestImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {


        try {

            return userService.signUp(requestMap);

        }

        catch (Exception e){

            e.printStackTrace();
        }

        return CafeUtils.getResponseEntity(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}