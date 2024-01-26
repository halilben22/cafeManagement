package com.halildev.cafeManagement.dao;

import com.halildev.cafeManagement.pojo.Product;
import com.halildev.cafeManagement.wrapper.ProductWrapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDao extends JpaRepository<Product,Long> {

    List<ProductWrapper>getAllProduct();
}
