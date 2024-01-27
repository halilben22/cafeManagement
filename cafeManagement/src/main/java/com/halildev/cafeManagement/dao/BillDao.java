package com.halildev.cafeManagement.dao;

import com.halildev.cafeManagement.pojo.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillDao extends JpaRepository<Bill, Long> {


}
