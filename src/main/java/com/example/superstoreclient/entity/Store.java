package com.example.superstoreclient.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "store")
@Data
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "manager_id")
    private Long managerId;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "store_address")
    private String storeAddress;

    @Lob
    @Column(name = "store_business_license")
    private byte[] storeBusinessLicense;

    @Column(name = "store_phone")
    private String storePhone;

    // getters and setters, constructors, etc.
}