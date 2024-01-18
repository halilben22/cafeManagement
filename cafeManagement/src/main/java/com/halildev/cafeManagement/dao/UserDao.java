package com.halildev.cafeManagement.dao;

import com.halildev.cafeManagement.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User,Long> {
}
