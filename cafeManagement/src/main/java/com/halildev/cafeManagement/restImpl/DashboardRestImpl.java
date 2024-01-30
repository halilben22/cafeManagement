package com.halildev.cafeManagement.restImpl;

import com.halildev.cafeManagement.rest.DashboardRest;
import com.halildev.cafeManagement.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class DashboardRestImpl implements DashboardRest {


   private final DashboardService dashboardService;

    public DashboardRestImpl(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @Override
    public ResponseEntity<Map<String, Object>> getDetails() {
      return dashboardService.getDetails();
    }
}
