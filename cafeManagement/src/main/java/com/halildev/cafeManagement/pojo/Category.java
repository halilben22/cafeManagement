package com.halildev.cafeManagement.pojo;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.List;

@NamedQuery(name = "Category.getAllCategory", query = "select c from Category c inner join Product p on c.id=p.category.id")

@Data
@Entity()

@DynamicUpdate
@DynamicInsert
@Table(name = "category")
public class Category implements Serializable {


    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;



}
