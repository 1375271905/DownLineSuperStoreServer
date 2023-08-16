package com.example.superstoreclient.controller;

import com.example.superstoreclient.entity.Category;
import com.example.superstoreclient.entity.CategoryItem;
import com.example.superstoreclient.form.SearchForm;
import com.example.superstoreclient.service.CategoryService;
import com.example.superstoreclient.util.ResultVOUtil;
import com.example.superstoreclient.vo.PageVO;
import com.example.superstoreclient.vo.ResultVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "分类管理")
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Operation(summary = "获取所有第三级分类信息")
    @GetMapping("/getThirdAll")
    public ResultVO getAllThirdLevelCategory(){
        List<Category> allThirdLevelCategory = categoryService.getAllThirdLevelCategory();
        if(allThirdLevelCategory!=null){
            return ResultVOUtil.success(allThirdLevelCategory);
        }else {
            return ResultVOUtil.fail();
        }
    }

    @Operation(summary = "获取三级分类所有信息")
    @GetMapping("/getAll")
    public ResultVO getAllLevelCategory(){
        List<CategoryItem> allLevelCategory = categoryService.getAllLevelCategory();
        if(allLevelCategory!=null){
            return ResultVOUtil.success(allLevelCategory);
        }else {
            return ResultVOUtil.fail();
        }
    }

    @Operation(summary = "通过三级分类信息获取商品信息")
    @PostMapping("/searchProduct")
    public ResultVO getThirdLevelCategory(@RequestBody SearchForm searchForm){
        PageVO thirdLevelCategory = categoryService.getThirdLevelCategory(searchForm);
        if(thirdLevelCategory!=null) {
            return ResultVOUtil.success(thirdLevelCategory);
        } else {
            return ResultVOUtil.fail();
        }
    }

}
