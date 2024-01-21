package com.halildev.cafeManagement.serviceImpl;

import com.halildev.cafeManagement.config.PasswordEncoderConfigure;
import com.halildev.cafeManagement.constants.CafeConstants;
import com.halildev.cafeManagement.dao.UserDao;
import com.halildev.cafeManagement.pojo.User;
import com.halildev.cafeManagement.security.CustomerUserDetailsService;
import com.halildev.cafeManagement.security.JwtUtils;
import com.halildev.cafeManagement.service.UserService;
import com.halildev.cafeManagement.utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;


@Slf4j
@Service
public class UserServiceImpl implements UserService {


    private final UserDao userDao;
    private final PasswordEncoderConfigure encoder;

    private final AuthenticationManager authenticationManager;

    private final CustomerUserDetailsService customerUserDetailsService;

    private final JwtUtils jwtUtils;

    public UserServiceImpl(UserDao userDao, PasswordEncoderConfigure encoder, AuthenticationManager authenticationManager, CustomerUserDetailsService customerUserDetailsService, JwtUtils jwtUtils) {
        this.userDao = userDao;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.customerUserDetailsService = customerUserDetailsService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {

        log.info("Inside signup {}", requestMap);


        try {


            if (validateSignUpMap(requestMap)) {
                User user = userDao.findByEmailId(requestMap.get("email"));

                if (Objects.isNull(user)) {

                    userDao.save(getUserFromMap(requestMap));


                    return CafeUtils.getResponseEntity("Successfully registered!", HttpStatus.OK);

                } else {

                    return CafeUtils.getResponseEntity(CafeConstants.EMAIL_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
                }

            } else {
                return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);


    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside login");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();




        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken( requestMap.get("email"),requestMap.get("password"))
            );




            if (auth.isAuthenticated()) {
                if (customerUserDetailsService.getUserDetails().getStatus().equalsIgnoreCase("true")) {
                    return new ResponseEntity<String>("{\"token\":\"" + jwtUtils.generateToken(customerUserDetailsService.getUserDetails().getEmail(), customerUserDetailsService.getUserDetails().getRole() + "\"}"),
                            HttpStatus.OK);
                } else {

                    return CafeUtils.getResponseEntity(CafeConstants.ADMIN_APPROVAL, HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception e) {
            log.error("{}", e);
        }

        return CafeUtils.getResponseEntity(CafeConstants.BAD_CREDENTIALS, HttpStatus.BAD_REQUEST);
    }


    private boolean validateSignUpMap(Map<String, String> requestMap) {

        return requestMap.containsKey("name") &&
                requestMap.containsKey("contactNumber") &&
                requestMap.containsKey("email") &&
                requestMap.containsKey("password");
    }

    private User getUserFromMap(Map<String, String> requestMap) {
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(encoder.passwordEncoder().encode(requestMap.get("password")));
        user.setStatus("false");
        user.setRole("user");


        return user;
    }
}
