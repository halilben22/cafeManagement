package com.halildev.cafeManagement.service;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;


public interface DashboardService {
    ResponseEntity<Map<String, Object>> getDetails();
}
