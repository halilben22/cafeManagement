package com.halildev.cafeManagement.dao;

import com.halildev.cafeManagement.pojo.User;
import com.halildev.cafeManagement.wrapper.UserWrapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserDao extends JpaRepository<User, Long> {

    User findByEmailId(@Param("email") String email);

    List<UserWrapper> getAllUser();


    @Transactional
    @Modifying
    Integer updateStatus(@Param("status") String status, @Param("id") Long id);

    List<String> getAllAdmin();

    User findByEmail(String email);



}
