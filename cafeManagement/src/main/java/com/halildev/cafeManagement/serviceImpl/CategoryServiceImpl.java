package com.halildev.cafeManagement.serviceImpl;

import com.halildev.cafeManagement.constants.CafeConstants;
import com.halildev.cafeManagement.dao.CategoryDao;
import com.halildev.cafeManagement.pojo.Category;
import com.halildev.cafeManagement.security.JwtAuthFilter;
import com.halildev.cafeManagement.service.CategoryService;
import com.halildev.cafeManagement.utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {


    private final CategoryDao categoryDao;

    private final JwtAuthFilter jwtAuthFilter;

    public CategoryServiceImpl(CategoryDao categoryDao, JwtAuthFilter jwtAuthFilter) {
        this.categoryDao = categoryDao;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try {
            if (jwtAuthFilter.isAdmin()) {
                if (validateCategoryMap(requestMap, false)) {
                    categoryDao.save(getCategoryFromMap(requestMap, false));

                    return CafeUtils.getResponseEntity("Category added successfully!", HttpStatus.OK);
                }

            } else {

                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_REQUEST, HttpStatus.UNAUTHORIZED);

            }
        } catch (Exception e) {

            e.printStackTrace();

        }

        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<List<Category>> getAllCategory(String filterValue) {
        try {
            if (!(filterValue == null) && !Strings.isEmpty(filterValue) && filterValue.equalsIgnoreCase("true")) {

                log.info("inside filter");
                return new ResponseEntity<>(categoryDao.getAllCategory(), HttpStatus.OK);
            }
            return new ResponseEntity<>(categoryDao.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try {


            if (jwtAuthFilter.isAdmin()) {
                if (validateCategoryMap(requestMap, true)) {
                    Optional optional = categoryDao.findById(Long.parseLong(requestMap.get("id")));
                    if (!optional.isEmpty()) {
                        categoryDao.save(getCategoryFromMap(requestMap, true));
                        return CafeUtils.getResponseEntity("Category succesfully updated!", HttpStatus.OK);
                    } else {

                        return CafeUtils.getResponseEntity("Category id doesnt exist", HttpStatus.OK);
                    }

                }

                return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);

            } else {


                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_REQUEST, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateCategoryMap(Map<String, String> requestMap, boolean validateId) {

        if (requestMap.containsKey("name")) {
            if (requestMap.containsKey("id") && validateId) {

                return true;
            } else return !validateId;
        }

        return false;
    }

    private Category getCategoryFromMap(Map<String, String> requestMap, Boolean isAdd) {

        Category category = new Category();

        if (isAdd) {
            category.setId(Long.parseLong(requestMap.get("id")));
        }
        category.setName(requestMap.get("name"));

        return category;
    }
}
