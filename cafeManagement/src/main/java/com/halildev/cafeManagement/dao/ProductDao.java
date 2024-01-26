package com.halildev.cafeManagement.dao;

import com.halildev.cafeManagement.pojo.Product;
import com.halildev.cafeManagement.wrapper.ProductWrapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDao extends JpaRepository<Product,Long> {

    List<ProductWrapper>getAllProduct();



    @Transactional
    @Modifying
    Integer updateProductStatus(@Param("status") String status,@Param("id") Long id);

    List<ProductWrapper> getProductByCategory(Long id);

    ProductWrapper getProductById(@Param("id") Long id);
}
