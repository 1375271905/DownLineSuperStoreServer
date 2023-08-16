package com.example.superstoreclient.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.superstoreclient.entity.Category;
import com.example.superstoreclient.entity.CategoryItem;
import com.example.superstoreclient.entity.Product;
import com.example.superstoreclient.form.SearchForm;
import com.example.superstoreclient.mapper.CategoryMapper;
import com.example.superstoreclient.mapper.ProductMapper;
import com.example.superstoreclient.service.CategoryService;
import com.example.superstoreclient.vo.PageVO;
import com.example.superstoreclient.vo.ProductVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    ProductMapper productMapper;

    @Override
    public List<Category> getAllThirdLevelCategory() {
        return categoryMapper.getAllThirdLevelCategory();
    }

    @Override
    public List<CategoryItem> getAllLevelCategory() {
        List<CategoryItem> result = new ArrayList<>();

        QueryWrapper<Category> leverOneWrapper = new QueryWrapper<>();
        leverOneWrapper.isNull("pid");
        List<Category> categoriesOne = categoryMapper.selectList(leverOneWrapper);

        for (Category one : categoriesOne) {
            CategoryItem categoryItemOne = new CategoryItem();
            categoryItemOne.setCategoryId(one.getCategoryId());
            categoryItemOne.setCategoryName(one.getCategoryName());
            categoryItemOne.setPid(one.getPid());

            QueryWrapper<Category> leverTwoWrapper = new QueryWrapper<>();
            leverTwoWrapper.eq("pid", categoryItemOne.getCategoryId());
            List<Category> categoriesTwo = categoryMapper.selectList(leverTwoWrapper);

            List<CategoryItem> levelTwoList = new ArrayList<>();
            for (Category category2 : categoriesTwo) {
                CategoryItem categoryItemTwo = new CategoryItem();
                categoryItemTwo.setCategoryId(category2.getCategoryId());
                categoryItemTwo.setCategoryName(category2.getCategoryName());
                categoryItemTwo.setPid(category2.getPid());

                QueryWrapper<Category> leverThreeWrapper = new QueryWrapper<>();
                leverThreeWrapper.eq("pid", categoryItemTwo.getCategoryId());
                List<Category> categoriesThree = categoryMapper.selectList(leverThreeWrapper);

                List<CategoryItem> levelThreeList = new ArrayList<>();
                for (Category category3 : categoriesThree) {
                    CategoryItem categoryItemThree = new CategoryItem();
                    categoryItemThree.setCategoryId(category3.getCategoryId());
                    categoryItemThree.setCategoryName(category3.getCategoryName());
                    categoryItemThree.setPid(category3.getPid());

                    levelThreeList.add(categoryItemThree);
                }

                categoryItemTwo.setCategoryItemList(levelThreeList);
                levelTwoList.add(categoryItemTwo);
            }

            categoryItemOne.setCategoryItemList(levelTwoList);
            result.add(categoryItemOne);
        }

        return result;
    }



    @Override
    public PageVO getThirdLevelCategory(SearchForm searchForm) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        List<Long> thirdLevelCategoryId = categoryMapper.getThirdLevelCategory(Integer.valueOf(searchForm.getValue()));
        if (thirdLevelCategoryId.isEmpty()) {
            return null; // 返回一个空的PageVO对象
        }
        queryWrapper.in("category_id", thirdLevelCategoryId);
        queryWrapper.eq("store_id", searchForm.getStoreId()).orderByAsc("product_id");

        System.out.println(searchForm);

        if(searchForm.getPageSize()==null){
            searchForm.setPageSize(10);
        }
        if(searchForm.getPageNum()==null){
            searchForm.setPageNum(1);
        }

        Page<Product> page = new Page<>(searchForm.getPageNum(), searchForm.getPageSize());
        IPage<Product> pageResult = productMapper.selectPage(page, queryWrapper);
        List<Product> productList = pageResult.getRecords();

        List<ProductVO> productVOList = new ArrayList<>();
        for (Product product : productList) {
            ProductVO productVO = new ProductVO();
            BeanUtils.copyProperties(product, productVO);
            productVOList.add(productVO);
        }

        PageVO pageVO = new PageVO();
        pageVO.setData(productVOList);
        pageVO.setTotal(pageResult.getTotal());

        return pageVO;
    }

}
