package com.example.superstoreclient.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.superstoreclient.entity.Store;
import com.example.superstoreclient.mapper.StoreMapper;
import com.example.superstoreclient.service.StoreService;
import com.example.superstoreclient.util.ResultVOUtil;
import com.example.superstoreclient.vo.ResultVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "店铺管理")
@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    StoreMapper storeMapper;

    @Autowired
    StoreService storeService;

    @Operation(summary = "新增店铺")
    @PutMapping("/save")
    public ResultVO save(@RequestBody Store store){
        if(storeService.storeSave(store)) {
            return ResultVOUtil.success(null);
        } else {
            return ResultVOUtil.fail();
        }
    }

    @Operation(summary = "根据负责人id获取店铺信息")
    @GetMapping("/findByManagerId/{id}")
    public ResultVO findByManagerId(@PathVariable("id") Integer id){
        Store store = storeService.findByManagerId(id);
        if(store != null){
            return ResultVOUtil.success(store);
        } else {
            return ResultVOUtil.fail();
        }
    }

    @Operation(summary = "根据负责人id获取店铺id")
    @GetMapping("/findStoreIdByManagerId/{id}")
    public ResultVO findStoreIdByManagerId(@PathVariable("id") Integer id){
        Long storeId = storeService.getStoreIdByManagerId(id);
        if(storeId != null){
            return ResultVOUtil.success(storeId);
        } else {
            return ResultVOUtil.fail();
        }
    }

    @Operation(summary = "根据负责人id修改店铺信息")
    @PutMapping("/update")
    public ResultVO update(@RequestBody Store store){
        if(storeService.storeUpdate(store)){
            return ResultVOUtil.success(null);
        } else {
            return ResultVOUtil.fail();
        }
    }
    @Operation(summary = "根据负责人id删除店铺")
    @DeleteMapping("/delete/{id}")
    public ResultVO delete(@PathVariable("id") Integer id){
        if(storeService.storeDelete(id)){
            return ResultVOUtil.success(null);
        }else {
            return ResultVOUtil.fail();
        }
    }
    @Operation(summary = "返回所有店铺信息")
    @GetMapping("/listAll")
    public ResultVO list(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        return ResultVOUtil.success(this.storeService.listAll(pageNum,pageSize));
    }

    @Operation(summary = "返回未通过审核的店铺信息")
    @GetMapping("/unApprovedStores")
    public ResultVO unApproveStoresList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        return ResultVOUtil.success(this.storeService.unApprovedStoresList(pageNum,pageSize));
    }


    @Operation(summary = "返回已通过审核的店铺信息")
    @GetMapping("/isApprovedStores")
    public ResultVO isApproveStoresList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        return ResultVOUtil.success(this.storeService.isApprovedStoresList(pageNum,pageSize));
    }
}
