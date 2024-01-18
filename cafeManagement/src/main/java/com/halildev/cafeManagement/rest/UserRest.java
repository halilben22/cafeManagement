package com.halildev.cafeManagement.rest;


import com.halildev.cafeManagement.pojo.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public interface UserRest  {


    @PostMapping("/signup")
    ResponseEntity<String> signUp(@RequestBody(required = true) Map<String, String> requestMap);




}
