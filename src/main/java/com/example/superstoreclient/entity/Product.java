package com.example.superstoreclient.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;


@Entity
@Table(name = "product")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "product_name")
    private String productName;

    @Lob
    @Column(name = "product_img")
    private byte[] productImg;

    @Column(name = "product_price")
    private BigDecimal productPrice;

    @Column(name = "product_stock")
    private Integer productStock;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "product_specification")
    private String productSpecification;

    @Column(name = "product_brand")
    private String productBrand;

    @Column(name = "product_company")
    private String productCompany;

    @Column(name = "category_id")
    private Long categoryId;

    // getters and setters, constructors, etc.
}