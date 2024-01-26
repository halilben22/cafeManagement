package com.halildev.cafeManagement.restImpl;

import com.halildev.cafeManagement.constants.CafeConstants;
import com.halildev.cafeManagement.rest.ProductRest;
import com.halildev.cafeManagement.service.ProductService;
import com.halildev.cafeManagement.utils.CafeUtils;
import com.halildev.cafeManagement.wrapper.ProductWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ProductRestImpl implements ProductRest {

    private final ProductService productService;

    public ProductRestImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try {
            return productService.addNewProduct(requestMap);
        } catch (Exception e) {


        }

        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getAllProduct() {

        try {


            return productService.getAllProduct();
        } catch (Exception e) {


        }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {

        try {

            return productService.updateProduct(requestMap);

        } catch (Exception e) {

        }

        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
