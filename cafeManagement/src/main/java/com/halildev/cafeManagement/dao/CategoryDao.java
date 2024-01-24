package com.halildev.cafeManagement.dao;

import com.halildev.cafeManagement.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryDao extends JpaRepository<Category, Long> {


    List<Category>getAllCategory();
}
