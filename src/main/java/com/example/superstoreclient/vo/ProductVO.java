package com.example.superstoreclient.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductVO {
    private Long productId;
    private String productName;
    private byte[] productImg;
    private BigDecimal productPrice;
    private Integer productStock;
    private String productCode;
    private String productSpecification;
    private String productBrand;
    private String productCompany;
    private Long categoryId;
}