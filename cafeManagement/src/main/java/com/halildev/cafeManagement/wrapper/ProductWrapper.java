package com.halildev.cafeManagement.wrapper;

import lombok.Data;

@Data
public class ProductWrapper {

    Long id;
    String name;

    String description;
    Integer price;
    String status;
    Long categoryId;
    String categoryName;

    public ProductWrapper() {
    }

    public ProductWrapper(Long id, String name, String description, Integer price, String status, Long categoryId, String categoryName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}
