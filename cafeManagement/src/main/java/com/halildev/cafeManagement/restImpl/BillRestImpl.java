package com.halildev.cafeManagement.restImpl;

import com.halildev.cafeManagement.constants.CafeConstants;
import com.halildev.cafeManagement.rest.BillRest;
import com.halildev.cafeManagement.service.BillService;
import com.halildev.cafeManagement.utils.CafeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class BillRestImpl implements BillRest {


 private final    BillService billService;

    public BillRestImpl(BillService billService) {
        this.billService = billService;
    }


    @Override
    public ResponseEntity<String> generateReport(Map<String, String> requestMap) {

        try {

        }

        catch (Exception e){


        }

       return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
