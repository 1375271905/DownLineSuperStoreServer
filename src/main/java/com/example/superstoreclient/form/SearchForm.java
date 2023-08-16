package com.example.superstoreclient.form;

import lombok.Data;

@Data
public class SearchForm {
    private Long storeId;
    private String key;
    private String value;
    private Integer pageNum;
    private Integer pageSize;
}
