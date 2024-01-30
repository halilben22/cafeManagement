package com.halildev.cafeManagement.restImpl;

import com.halildev.cafeManagement.constants.CafeConstants;
import com.halildev.cafeManagement.dao.CategoryDao;
import com.halildev.cafeManagement.pojo.Category;
import com.halildev.cafeManagement.rest.CategoryRest;
import com.halildev.cafeManagement.service.CategoryService;
import com.halildev.cafeManagement.utils.CafeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
public class CategoryRestImpl implements CategoryRest {

    private final CategoryService categoryService;

    public CategoryRestImpl(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try {
            return categoryService.addNewCategory(requestMap);

        } catch (Exception e) {

            e.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
        try {

            return categoryService.getAllCategory(filterValue);

        }
        catch (Exception e){


        }

        return CafeUtils.getResponseEntityAsList(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
     try {
     return     categoryService.updateCategory(requestMap);
     }

     catch (Exception e){

         e.printStackTrace();
     }

     return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
