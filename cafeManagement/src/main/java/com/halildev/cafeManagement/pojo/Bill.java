package com.halildev.cafeManagement.pojo;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;


@Data
@Entity
@DynamicInsert
@DynamicUpdate
public class Bill implements Serializable {


    private static final long serialVersionUID = 1L;


    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;


    @Column(name = "uuid")
    private String uuid;

    @Column(name = "name")
    private String name;


    @Column(name = "email")
    private String email;


    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "total")
    private Integer total;

    @Column(name = "product_details",columnDefinition = "json")
    private String productDetail;



    @Column(name = "createdby")
    private String createdBy;

}
