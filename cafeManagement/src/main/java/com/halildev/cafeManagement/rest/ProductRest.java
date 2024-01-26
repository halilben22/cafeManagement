package com.halildev.cafeManagement.rest;

import com.halildev.cafeManagement.wrapper.ProductWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RequestMapping("/product")
public interface ProductRest {

    @PostMapping("/add")
    ResponseEntity<String> addNewProduct(@RequestBody Map<String,String>requestMap);

    @GetMapping("/get")
    ResponseEntity<List<ProductWrapper>>getAllProduct();



    @PostMapping("/update")
    ResponseEntity<String>updateProduct(@RequestBody Map<String,String> requestMap);
}
