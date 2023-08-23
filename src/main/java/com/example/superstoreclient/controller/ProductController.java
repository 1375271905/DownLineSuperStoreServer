package com.example.superstoreclient.controller;
import com.example.superstoreclient.dto.ProductStock;
import com.example.superstoreclient.entity.Product;
import com.example.superstoreclient.form.SearchForm;
import com.example.superstoreclient.service.ProductService;
import com.example.superstoreclient.service.StoreService;
import com.example.superstoreclient.util.ResultVOUtil;
import com.example.superstoreclient.vo.ResultVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Tag(name = "商品管理")
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private StoreService storeService;

    @Operation(summary = "分页商品查询")
    @GetMapping("/list/{storeId}")
    public ResultVO<?> list(@PathVariable Long storeId,
                         @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        if(storeService.storeIsApprove(storeId)){
            return ResultVOUtil.success(this.productService.list(storeId, pageNum, pageSize));
        }else {
            return ResultVOUtil.fail();
        }

    }

    @Operation(summary = "根据商品名查询商品")
    @PostMapping("/search")
    public ResultVO<?> search(@RequestBody SearchForm searchForm){
        if(storeService.storeIsApprove(searchForm.getStoreId())){
            return ResultVOUtil.success(this.productService.search(searchForm));
        }else {
            return ResultVOUtil.fail();
        }

    }

    @Operation(summary = "根据店铺id与商品编码查询商品")
    @PostMapping("/searchByProductCode")
    public ResultVO<?> selectProductByProductCode(@RequestBody Product product){
        if(storeService.storeIsApprove(product.getStoreId())){
            return ResultVOUtil.success(this.productService.selectProductByProductCode(product));
        }else {
            return ResultVOUtil.fail();
        }

    }

    @Operation(summary = "根据店铺id与商品编码修改商品信息")
    @PutMapping("/update")
    public ResultVO<?> update(@RequestBody Product product){
        if(storeService.storeIsApprove(product.getStoreId())){
            Boolean update = this.productService.update(product);
            if(!update) {
                return ResultVOUtil.fail();
            }
            return ResultVOUtil.success(null);
        }else {
            return ResultVOUtil.fail();
        }

    }

//    @PutMapping("/categoryIn")
//    public ResultVO categoryIn(@RequestParam(value = "productId") Long productId,
//                               @RequestParam(value = "Num") Integer Num){
//        Boolean updateIn = this.productService.updateIn(productId,num);
//        if(!updateIn) return ResultVOUtil.fail();
//        return ResultVOUtil.success(null);
//    }

    @Operation(summary = "入库")
    @PutMapping("/stockIn")
    public ResultVO<?> stockIn(@RequestBody ProductStock productStock){
        if(storeService.storeIsApprove(productStock.getProduct().getStoreId())){
            Boolean updateIn = this.productService.stockIn(productStock);
            if(!updateIn) {
                return ResultVOUtil.fail();
            }
            return ResultVOUtil.success(null);
        }else {
            return ResultVOUtil.fail();
        }

    }

    @Operation(summary = "出库")
    @PutMapping("/stockOut")
    public ResultVO<?> stockOut(@RequestBody ProductStock productStock){
        if(storeService.storeIsApprove(productStock.getProduct().getStoreId())){
            Boolean updateIn = this.productService.stockOut(productStock);
            if(!updateIn) {
                return ResultVOUtil.fail();
            }
            return ResultVOUtil.success(null);
        }else {
            return ResultVOUtil.fail();
        }
    }

    @Operation(summary = "批量出库")
    @PostMapping("/batchStockOut")
    public ResultVO<?> batchStockOut(@RequestBody List<ProductStock> productStockList) {
        List<ProductStock> failedStocks = new ArrayList<>();
        for (ProductStock productStock : productStockList) {
            if (storeService.storeIsApprove(productStock.getProduct().getStoreId())) {
                Boolean updateSuccess = this.productService.stockOut(productStock);
                if (!updateSuccess) {
                    failedStocks.add(productStock);
                }
            } else {
                failedStocks.add(productStock);
            }
        }
        if (failedStocks.isEmpty()) {
            return ResultVOUtil.success(null);
        } else {
            return ResultVOUtil.fail("Some products could not be stocked out.", failedStocks);
        }
    }


    @Operation(summary = "新增商品")
    @PostMapping("/save")
    public ResultVO<?> save(@RequestBody Product product){
        if(storeService.storeIsApprove(product.getStoreId())){
            if(productService.productSave(product)) {
                return ResultVOUtil.success(null);
            } else {
                return ResultVOUtil.fail();
            }
        }else {
            return ResultVOUtil.fail();
        }

    }

    @Operation(summary = "删除商品")
    @DeleteMapping("/delete/{productId}/{storeId}")
    public ResultVO<?> delete(@PathVariable Long productId,@PathVariable Long storeId){
        if(storeService.storeIsApprove(storeId)){
            if(productService.productDelete(productId)) {
                return ResultVOUtil.success(null);
            } else {
                return ResultVOUtil.fail();
            }
        }else {
            return ResultVOUtil.fail();
        }

    }
}