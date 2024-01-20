package com.halildev.cafeManagement.security;

import com.halildev.cafeManagement.dao.UserDao;
import com.halildev.cafeManagement.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Slf4j
@Service
public class CustomerUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    private User userDetail;

    public CustomerUserDetailsService(UserDao userDao) {
        this.userDao = userDao;

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      log.info("inside loadUserByUsername");

        userDetail = userDao.findByEmailId(email);

        if (!Objects.isNull(userDetail)) {

            return new org.springframework.security.core.userdetails.User(userDetail.getEmail(), userDetail.getPassword(), new ArrayList<>());
        }
        throw new UsernameNotFoundException("User not found");

    }


    public User getUserDetails() {

        return userDetail;
    }
}
