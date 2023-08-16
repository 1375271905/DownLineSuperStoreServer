package com.example.superstoreclient.controller;
import com.example.superstoreclient.dto.ProductStock;
import com.example.superstoreclient.entity.Product;
import com.example.superstoreclient.form.SearchForm;
import com.example.superstoreclient.service.ProductService;
import com.example.superstoreclient.util.ResultVOUtil;
import com.example.superstoreclient.vo.ResultVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Tag(name = "商品管理")
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "分页商品查询")
    @GetMapping("/list/{storeId}")
    public ResultVO<?> list(@PathVariable Long storeId,
                         @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        return ResultVOUtil.success(this.productService.list(storeId, pageNum, pageSize));
    }

    @Operation(summary = "根据商品名查询商品")
    @PostMapping("/search")
    public ResultVO<?> search(@RequestBody SearchForm searchForm){
        return ResultVOUtil.success(this.productService.search(searchForm));
    }

    @Operation(summary = "根据店铺id与商品编码查询商品")
    @PostMapping("/searchByProductCode")
    public ResultVO<?> selectProductByProductCode(@RequestBody Product product){
        return ResultVOUtil.success(this.productService.selectProductByProductCode(product));
    }

    @Operation(summary = "根据店铺id与商品编码修改商品信息")
    @PutMapping("/update")
    public ResultVO<?> update(@RequestBody Product product){
        Boolean update = this.productService.update(product);
        if(!update) {
            return ResultVOUtil.fail();
        }
        return ResultVOUtil.success(null);
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
        Boolean updateIn = this.productService.stockIn(productStock);
        if(!updateIn) {
            return ResultVOUtil.fail();
        }
        return ResultVOUtil.success(null);
    }

//    @PutMapping("/categoryOut")
//    public ResultVO categoryOut(@RequestParam(value = "productId") Long productId,
//                                @RequestParam(value = "Num") Integer Num){
//        Boolean updateOut = this.productService.updateOut(productId,Num);
//        if(!updateOut) return ResultVOUtil.fail();
//        return ResultVOUtil.success(null);
//    }

    @Operation(summary = "出库")
    @PutMapping("/stockOut")
    public ResultVO<?> stockOut(@RequestBody ProductStock productStock){
        Boolean updateIn = this.productService.stockOut(productStock);
        if(!updateIn) {
            return ResultVOUtil.fail();
        }
        return ResultVOUtil.success(null);
    }

    @Operation(summary = "新增商品")
    @PostMapping("/save")
    public ResultVO<?> save(@RequestBody Product product){
        if(productService.productSave(product)) {
            return ResultVOUtil.success(null);
        } else {
            return ResultVOUtil.fail();
        }
    }

    @Operation(summary = "删除商品")
    @DeleteMapping("/delete/{productId}")
    public ResultVO<?> delete(@PathVariable Long productId){
        if(productService.productDelete(productId)) {
            return ResultVOUtil.success(null);
        } else {
            return ResultVOUtil.fail();
        }
    }
}