package com.halildev.cafeManagement.dao;

import com.halildev.cafeManagement.pojo.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BillDao extends JpaRepository<Bill, Long> {





    List<Bill> getAllBills();

    List<Bill> getBillByUsername(@Param("username") String currentUser);
}
