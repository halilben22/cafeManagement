package com.halildev.cafeManagement.pojo;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;


@NamedQuery(name = "User.findByEmailId",query = "select u from User u where u.email=:email")
@NamedQuery(name = "User.getAllUser",query = "select new com.halildev.cafeManagement.wrapper.UserWrapper(u.id,u.name,u.email,u.contactNumber,u.status) from User u where u.role='user'")
@NamedQuery(name = "User.getAllAdmin",query = "select u.email from User u where u.role='admin'")
@NamedQuery(name = "User.updateStatus",query = "update User u set u.status=:status where u.id=:id")
@Data
@Entity()

@DynamicUpdate
@DynamicInsert
@Table(name = "user")

public class User implements Serializable {

    private static final long serialVersionUID = 1L;


    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "name")
    private String name;

    @Column(name = "contactNumber")
    private String contactNumber;
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;

    @Column(name = "role")
    private String role;


}
