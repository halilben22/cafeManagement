package com.halildev.cafeManagement.rest;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RequestMapping("/user")
public interface UserRest  {


    @PostMapping("/signup")
    ResponseEntity<String> signUp(@RequestBody() Map<String, String> requestMap);




    @PostMapping("/login")

    ResponseEntity<String> login(@RequestBody Map<String,String> requestMap);


}
