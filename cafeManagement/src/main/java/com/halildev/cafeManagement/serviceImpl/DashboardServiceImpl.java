package com.halildev.cafeManagement.serviceImpl;


import com.halildev.cafeManagement.dao.BillDao;
import com.halildev.cafeManagement.dao.CategoryDao;
import com.halildev.cafeManagement.dao.ProductDao;
import com.halildev.cafeManagement.dao.UserDao;
import com.halildev.cafeManagement.service.DashboardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final CategoryDao categoryDao;

    private final ProductDao productDao;

    private final UserDao userDao;

    private final BillDao billDao;

    public DashboardServiceImpl(CategoryDao categoryDao, ProductDao productDao, UserDao userDao, BillDao billDao) {
        this.categoryDao = categoryDao;
        this.productDao = productDao;
        this.userDao = userDao;
        this.billDao = billDao;
    }

    @Override
    public ResponseEntity<Map<String, Object>> getDetails() {
        Map<String, Object> map = new HashMap<>();

        map.put("category", categoryDao.count());
        map.put("product", productDao.count());
        map.put("bill", billDao.count());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
