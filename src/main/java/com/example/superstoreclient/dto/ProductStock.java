package com.example.superstoreclient.dto;

import com.example.superstoreclient.entity.Product;
import lombok.Data;

@Data
public class ProductStock {
    private Product product;
    private Integer num;
}
