package com.halildev.cafeManagement.serviceImpl;

import com.halildev.cafeManagement.config.PasswordEncoderConfigure;
import com.halildev.cafeManagement.constants.CafeConstants;
import com.halildev.cafeManagement.dao.UserDao;
import com.halildev.cafeManagement.pojo.User;
import com.halildev.cafeManagement.security.CustomerUserDetailsService;
import com.halildev.cafeManagement.security.JwtAuthFilter;
import com.halildev.cafeManagement.security.JwtUtils;
import com.halildev.cafeManagement.service.UserService;
import com.halildev.cafeManagement.utils.CafeUtils;
import com.halildev.cafeManagement.utils.EmailUtils;
import com.halildev.cafeManagement.wrapper.UserWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Slf4j
@Service
public class UserServiceImpl implements UserService {


    private final UserDao userDao;
    private final PasswordEncoderConfigure encoder;

    private final AuthenticationManager authenticationManager;

    private final CustomerUserDetailsService customerUserDetailsService;

    private final JwtUtils jwtUtils;


    private final EmailUtils emailUtils;

    private final JwtAuthFilter jwtAuthFilter;

    public UserServiceImpl(UserDao userDao, PasswordEncoderConfigure encoder, AuthenticationManager authenticationManager, CustomerUserDetailsService customerUserDetailsService, JwtUtils jwtUtils, EmailUtils emailUtils, JwtAuthFilter jwtAuthFilter) {
        this.userDao = userDao;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.customerUserDetailsService = customerUserDetailsService;
        this.jwtUtils = jwtUtils;
        this.emailUtils = emailUtils;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {//sign up stuffs...

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
    public ResponseEntity<String> login(Map<String, String> requestMap) {//login to the website with jwt token.
        log.info("Inside login");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password"))
            );


            if (auth.isAuthenticated()) {
                if (customerUserDetailsService.getUserDetails().getStatus().equalsIgnoreCase("true")) {
                    return new ResponseEntity<String>(jwtUtils.generateToken(customerUserDetailsService.getUserDetails().getEmail(), customerUserDetailsService.getUserDetails().getRole()),
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

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {//returning all users from database.

        try {
            if (jwtAuthFilter.isAdmin()) {
                return new ResponseEntity<>(userDao.getAllUser(), HttpStatus.OK);
            } else {

                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {//This func for updating per user status as an admin.
        try {

            if (jwtAuthFilter.isAdmin()) {
                Optional<User> user = userDao.findById(Long.parseLong(requestMap.get("id")));


                if (user.isPresent()) {

                    userDao.updateStatus(requestMap.get("status"), Long.parseLong(requestMap.get("id")));

                    // sendMailToAllAdmin(requestMap.get("status"), user.get().getEmail(), userDao.getAllAdmin());//Code block for mail sending with JavaMailSender.Haven't finished yet and throwing some errors.
                    return CafeUtils.getResponseEntity("User status updated successfully!", HttpStatus.OK);
                } else {

                    return new ResponseEntity<String>("user does not exist!", HttpStatus.BAD_REQUEST);
                }
            } else {

                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_REQUEST, HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {


            e.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> checkToken() {
        return CafeUtils.getResponseEntity("true", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try {
            User user = userDao.findByEmail(jwtAuthFilter.getCurrentUser());
            System.out.printf(jwtAuthFilter.getCurrentUser());
            if (user != null) {

                    user.setPassword(requestMap.get("newPassword"));
                    userDao.save(user);
                    return CafeUtils.getResponseEntity("Password updated successfully!", HttpStatus.OK);



            } else {

                return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void sendMailToAllAdmin(String status, String user, List<String> allAdmin) {

        allAdmin.remove(jwtAuthFilter.getCurrentUser());
        if (status != null && status.equalsIgnoreCase("true")) {
            emailUtils.sendSimpleMessage(jwtAuthFilter.getCurrentUser(), "Account Approved", "USER:- " + user + "\n is approved by  \n ADMIN:" + jwtAuthFilter.getCurrentUser() + ")", allAdmin);

        } else {

            emailUtils.sendSimpleMessage(jwtAuthFilter.getCurrentUser(), "Account Disabled", "USER:- " + user + "\n is disabled by  \n ADMIN:" + jwtAuthFilter.getCurrentUser() + ")", allAdmin);

        }

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
