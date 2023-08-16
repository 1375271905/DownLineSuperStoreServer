package com.example.superstoreclient.entity;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryItem{

    private Long categoryId;

    private String categoryName;

    private Long pid;

    private List<CategoryItem> categoryItemList;

}
