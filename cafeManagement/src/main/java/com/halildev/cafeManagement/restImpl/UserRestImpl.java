package com.halildev.cafeManagement.restImpl;

import com.halildev.cafeManagement.constants.CafeConstants;
import com.halildev.cafeManagement.rest.UserRest;
import com.halildev.cafeManagement.service.UserService;
import com.halildev.cafeManagement.utils.CafeUtils;
import com.halildev.cafeManagement.wrapper.UserWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.halildev.cafeManagement.constants.CafeConstants.SOMETHING_WENT_WRONG;


@RestController
public class UserRestImpl implements UserRest {


    private final UserService userService;

    public UserRestImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {


        try {

            return userService.signUp(requestMap);

        } catch (Exception e) {

            e.printStackTrace();
        }

        return CafeUtils.getResponseEntity(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {


        try {
            return userService.login(requestMap);
        } catch (Exception e) {

            e.printStackTrace();
        }


        return CafeUtils.getResponseEntity(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {


        try {
            return userService.getAllUser();

        } catch (Exception e) {
            e.printStackTrace();

        }


        return CafeUtils.getResponseEntityAsList(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            return userService.update(requestMap);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return CafeUtils.getResponseEntity(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> checkToken() {
        try {

            return userService.checkToken();
        }

        catch (Exception e){
            e.printStackTrace();
        }

        return CafeUtils.getResponseEntity(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try {
         return userService.changePassword(requestMap);
        }

        catch (Exception e){

        }

        return CafeUtils.getResponseEntity(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
