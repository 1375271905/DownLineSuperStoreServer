package com.example.superstoreclient.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.superstoreclient.dto.ProductStock;
import com.example.superstoreclient.entity.Product;
import com.example.superstoreclient.form.SearchForm;
import com.example.superstoreclient.mapper.ProductMapper;
import com.example.superstoreclient.service.ProductService;
import com.example.superstoreclient.vo.PageVO;
import com.example.superstoreclient.vo.ProductVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private ProductMapper productMapper;
    @Override
    public PageVO list(Long storeId, int pageNum, int pageSize) {
        Page<Product> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        // 根据店铺ID进行筛选
        queryWrapper.eq("store_id", storeId).orderByAsc("product_id");
        Page<Product> resultPage = productMapper.selectPage(page, queryWrapper);
        List<Product> productList = resultPage.getRecords();
        //VO转换
        List<ProductVO> productVOList = new ArrayList<>();
        for (Product product : productList) {
            ProductVO productVO = new ProductVO();
            BeanUtils.copyProperties(product, productVO);
            productVOList.add(productVO);
        }
        PageVO pageVO = new PageVO();
        pageVO.setData(productVOList);
        pageVO.setTotal(resultPage.getTotal());
        System.out.println(resultPage.getTotal());
        return pageVO;
    }
    @Override
    public PageVO search(SearchForm searchForm) {

        if(searchForm.getPageNum()==null){
            searchForm.setPageNum(1);
        }
        if(searchForm.getPageSize()==null){
            searchForm.setPageSize(10);
        }

        Page<Product> page = new Page<>(searchForm.getPageNum(), searchForm.getPageSize());
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("store_id", searchForm.getStoreId()).orderByAsc("product_id"); // 根据店铺ID进行筛选
        queryWrapper.like("product_name", searchForm.getValue()); // 添加模糊查询条件，"关键词"是你要搜索的商品名称的关键词
        Page<Product> resultPage = productMapper.selectPage(page, queryWrapper);
        List<Product> productList = resultPage.getRecords();
        //VO转换
        List<ProductVO> productVOList = new ArrayList<>();
        for (Product product : productList) {
            ProductVO productVO = new ProductVO();
            BeanUtils.copyProperties(product, productVO);
            productVOList.add(productVO);
        }
        PageVO pageVO = new PageVO();
        pageVO.setData(productVOList);
        pageVO.setTotal(resultPage.getTotal());
        return pageVO;
    }
    @Override
    public Boolean update(Product product) {
        // 设置更新条件
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("store_id",product.getStoreId());
        queryWrapper.eq("product_code",product.getProductCode());
        // 执行更新操作
        int affectedRows = productMapper.update(product, queryWrapper);
        return affectedRows != 0;
    }
    @Override
    public Boolean updateIn(Long productId,Integer Num){
        UpdateWrapper<Product> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("product_id",productId);
        updateWrapper.setSql("product_stock = product_stock + " + Num); // 将product_stock字段加上Num的值
        int affectedRows = productMapper.update(null, updateWrapper);
        return affectedRows > 0;
    }

    @Override
    public Boolean updateOut(Long productId,Integer Num){
        UpdateWrapper<Product> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("product_id",productId);
        updateWrapper.setSql("product_stock = product_stock - " + Num); // 将product_stock字段加上Num的值
        int affectedRows = productMapper.update(null, updateWrapper);
        return affectedRows > 0;
    }
    @Override
    public Product selectProductByProductCode(Product product) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("store_id",product.getStoreId());
        queryWrapper.eq("product_code",product.getProductCode());
        return productMapper.selectOne(queryWrapper);
    }
    @Override
    public Boolean productSave(Product product) {
        int isInsert = productMapper.insert(product);
        return isInsert != 0;
    }
    @Override
    public Boolean productDelete(Long productId) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id",productId);
        int isDeleted = productMapper.delete(queryWrapper);
        return isDeleted != 0;
    }

    @Override
    public Boolean stockIn(ProductStock productStock) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("store_id",productStock.getProduct().getStoreId());
        queryWrapper.eq("product_code",productStock.getProduct().getProductCode());
        Product product = productMapper.selectOne(queryWrapper);
        if(product!=null){
            UpdateWrapper<Product> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("store_id",productStock.getProduct().getStoreId());
            updateWrapper.eq("product_code",productStock.getProduct().getProductCode());
            updateWrapper.setSql("product_stock = product_stock + " + productStock.getNum()); // 将product_stock字段加上Num的值
            int affectedRows = productMapper.update(null,updateWrapper);
            return affectedRows > 0;
        }
        else {
            productStock.getProduct().setProductStock(productStock.getNum());
            return this.productSave(productStock.getProduct());
        }
    }

    @Override
    public Boolean stockOut(ProductStock productStock) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("store_id",productStock.getProduct().getStoreId());
        queryWrapper.eq("product_code",productStock.getProduct().getProductCode());
        Product product = productMapper.selectOne(queryWrapper);
        if(product!=null){
            if(product.getProductStock()<=0||productStock.getNum()>product.getProductStock()){
                return false;
            }
            UpdateWrapper<Product> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("store_id",productStock.getProduct().getStoreId());
            updateWrapper.eq("product_code",productStock.getProduct().getProductCode());
            updateWrapper.setSql("product_stock = product_stock - " + productStock.getNum()); // 将product_stock字段减去Num的值
            int affectedRows = productMapper.update(null,updateWrapper);
            return affectedRows > 0;
        }
        else {
            return false;
        }
    }

}
