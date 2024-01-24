package com.halildev.cafeManagement.rest;


import com.halildev.cafeManagement.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RequestMapping("/user")
public interface UserRest {


    @PostMapping("/signup")
    ResponseEntity<String> signUp(@RequestBody() Map<String, String> requestMap);


    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody Map<String, String> requestMap);


    @GetMapping("/get")
    ResponseEntity<List<UserWrapper>> getAllUser();


    @PostMapping("/update")
    ResponseEntity<String> update(@RequestBody Map<String, String> requestMap);



    @GetMapping("/checkToken")
    ResponseEntity<String>checkToken();

    @PostMapping("/changePassword")
    ResponseEntity<String> changePassword(@RequestBody Map<String,String> requestMap);
}
