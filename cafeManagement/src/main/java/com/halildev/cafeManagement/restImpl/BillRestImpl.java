package com.halildev.cafeManagement.restImpl;

import com.halildev.cafeManagement.constants.CafeConstants;
import com.halildev.cafeManagement.pojo.Bill;
import com.halildev.cafeManagement.rest.BillRest;
import com.halildev.cafeManagement.service.BillService;
import com.halildev.cafeManagement.utils.CafeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
public class BillRestImpl implements BillRest {


    private final BillService billService;

    public BillRestImpl(BillService billService) {
        this.billService = billService;
    }


    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {

        try {


            return billService.generateReport(requestMap);
        } catch (Exception e) {
            e.printStackTrace();

        }

        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Bill>> getBills() {

        try {

            return billService.getBills();

        } catch (Exception e) {


        }


        return CafeUtils.getResponseEntityAsList(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
        try {
            return billService.getPdf(requestMap);
        } catch (Exception e) {


        }

        return null;
    }

    @Override
    public ResponseEntity<String> deleteBill(Long id) {
        try {

            return billService.deleteBill(id);

        }

        catch (Exception e){

            e.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.OK);
    }
}
