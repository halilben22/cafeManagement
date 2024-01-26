package com.halildev.cafeManagement.rest;

import com.halildev.cafeManagement.wrapper.ProductWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


     @PostMapping("/delete/{id}")
    ResponseEntity<String> deleteProduct(@PathVariable Long id);

     @PostMapping("/updateStatus")
    ResponseEntity<String> updateStatus(@RequestBody Map<String,String> requestMap);

     @GetMapping("/getByCategory/{id}")
    ResponseEntity<List<ProductWrapper>>getByCategory(@PathVariable Long id);


     @GetMapping("/getById/{id}")
    ResponseEntity<ProductWrapper>getProductById(@PathVariable Long id );

}
