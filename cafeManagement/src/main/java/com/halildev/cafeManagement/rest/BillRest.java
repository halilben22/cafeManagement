package com.halildev.cafeManagement.rest;


import com.halildev.cafeManagement.pojo.Bill;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/bill")
public interface BillRest {

    @PostMapping("/generateReport")
    ResponseEntity<String> generateReport(@RequestBody Map<String, Object> requestMap);


    @GetMapping("/getBills")
    ResponseEntity<List<Bill>> getBills();


    @PostMapping("/getPdf")
    ResponseEntity<byte[]>getPdf(@RequestBody Map<String,Object> requestMap);


    @PostMapping("/delete/{id}")
    ResponseEntity<String>deleteBill(@PathVariable Long id);
}
