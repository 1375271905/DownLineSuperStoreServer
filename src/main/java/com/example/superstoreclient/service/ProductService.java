package com.example.superstoreclient.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.superstoreclient.dto.ProductStock;
import com.example.superstoreclient.entity.Product;
import com.example.superstoreclient.form.SearchForm;
import com.example.superstoreclient.vo.PageVO;

public interface ProductService extends IService<Product> {

    public PageVO list(Long storeId, int pageNum, int pageSize);

    public PageVO search(SearchForm searchForm);
    public Boolean update(Product product);

    public Boolean updateIn(Long productId,Integer Num);
    public Boolean updateOut(Long productId,Integer Num);

    public Product selectProductByProductCode(Product product);

    public Boolean productSave(Product product);

    public Boolean productDelete(Long productId);

    public Boolean stockIn(ProductStock productStock);

    Boolean stockOut(ProductStock productStock);
}