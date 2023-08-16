package com.example.superstoreclient.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.superstoreclient.entity.Category;
import com.example.superstoreclient.entity.CategoryItem;
import com.example.superstoreclient.entity.Product;
import com.example.superstoreclient.form.SearchForm;
import com.example.superstoreclient.vo.PageVO;

import java.util.List;

public interface CategoryService extends IService<Category> {
    List<Category> getAllThirdLevelCategory();
    List<CategoryItem> getAllLevelCategory();
    PageVO getThirdLevelCategory(SearchForm categorySearchForm);
}
