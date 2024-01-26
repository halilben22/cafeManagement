package com.halildev.cafeManagement.serviceImpl;

import com.halildev.cafeManagement.constants.CafeConstants;
import com.halildev.cafeManagement.dao.ProductDao;
import com.halildev.cafeManagement.pojo.Category;
import com.halildev.cafeManagement.pojo.Product;
import com.halildev.cafeManagement.security.JwtAuthFilter;
import com.halildev.cafeManagement.service.ProductService;
import com.halildev.cafeManagement.utils.CafeUtils;
import com.halildev.cafeManagement.wrapper.ProductWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;
    private final JwtAuthFilter jwtAuthFilter;

    public ProductServiceImpl(ProductDao productDao, JwtAuthFilter jwtAuthFilter) {
        this.productDao = productDao;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try {
            if (jwtAuthFilter.isAdmin()) {
                if (validateProductMap(requestMap, false)) {
                    productDao.save(getProductFromMap(requestMap, false));
                    return CafeUtils.getResponseEntity("Product Added Successfully!", HttpStatus.OK);
                } else {

                    return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
                }
            } else {

                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_REQUEST, HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {


        }

        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getAllProduct() {


        try {
            return new ResponseEntity<>(productDao.getAllProduct(), HttpStatus.OK);
        } catch (Exception e) {

        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {

        try {


            if (jwtAuthFilter.isAdmin()) {

                if (validateProductMap(requestMap, true)) {
                    Optional<Product> optionalProduct = productDao.findById(Long.parseLong(requestMap.get("id")));


                    if (optionalProduct.isPresent()) {
                        Product product = getProductFromMap(requestMap, true);
                        product.setStatus(optionalProduct.get().getStatus());
                        productDao.save(product);
                        return CafeUtils.getResponseEntity("Product updated successfully!", HttpStatus.OK);
                    }
                    return CafeUtils.getResponseEntity("Product id does not exist!", HttpStatus.BAD_REQUEST);
                } else {

                    return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
                }
            } else {

                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_REQUEST, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd) {
        Category category = new Category();

        category.setId(Long.parseLong(requestMap.get("categoryId")));
        Product product = new Product();

        if (isAdd) {

            product.setId(Long.parseLong(requestMap.get("id")));
        } else {

            product.setStatus("true");
        }
        product.setCategory(category);
        product.setName(requestMap.get("name"));
        product.setDescription(requestMap.get("description"));
        product.setPrice(Integer.parseInt(requestMap.get("price")));

        return product;
    }

    private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {
        if (requestMap.containsKey("name")) {
            if (requestMap.containsKey("id") && validateId) {
                return true;
            } else return !validateId;
        }
        return false;
    }
}
