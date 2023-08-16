package com.example.superstoreclient;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.superstoreclient.controller.CategoryController;
import com.example.superstoreclient.controller.StoreController;
import com.example.superstoreclient.entity.Category;
import com.example.superstoreclient.entity.Product;
import com.example.superstoreclient.entity.Store;
import com.example.superstoreclient.mapper.CategoryMapper;
import com.example.superstoreclient.mapper.StoreMapper;
import com.example.superstoreclient.vo.ResultVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;


import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
public class SuperStoreClientApplicationTests1 {

    @Autowired
    private CategoryController categoryController;

    @Autowired // 使用MockBean注解来模拟StoreMapper对象
    private CategoryMapper categoryMapper;

    @Test
    void test1() {
        Integer pageNo = 1;
        Integer pageSize = 5;
        Page<Category> page = new Page<>(pageNo, pageSize);

        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        // 设置查询条件，如果有的话
        // queryWrapper.eq("field", value);

        // 使用分页插件进行分页查询
        IPage<Category> categoryPage = categoryMapper.selectPage(page, queryWrapper);

        List<Category> allThirdLevelCategory = categoryPage.getRecords();
        for (Category category : allThirdLevelCategory) {
            System.out.println(category);
        }

        System.out.println("------------------------------------------");

        // 获取当前页的数据列表
        List<Category> records = categoryPage.getRecords();
        for (Category category : records) {
            System.out.println(category);
        }

        System.out.println("总记录数：" + categoryPage.getTotal());
        System.out.println("总页数：" + categoryPage.getPages());
    }



    // Add more test methods as needed...
}
