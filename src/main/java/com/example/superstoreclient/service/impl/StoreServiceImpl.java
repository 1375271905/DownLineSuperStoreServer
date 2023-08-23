package com.example.superstoreclient.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.superstoreclient.entity.Store;
import com.example.superstoreclient.mapper.StoreMapper;
import com.example.superstoreclient.service.StoreService;
import com.example.superstoreclient.util.ResultVOUtil;
import com.example.superstoreclient.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class StoreServiceImpl extends ServiceImpl<StoreMapper,Store> implements StoreService {
    @Autowired
    StoreMapper storeMapper;

    @Override
    public Boolean storeSave(Store store) {
        int isInsert = storeMapper.insert(store);
        return isInsert != 0;
    }
    @Override
    public Store findByManagerId(Integer id) {
        QueryWrapper<Store> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("manager_id", id);
        return storeMapper.selectOne(queryWrapper);
    }

    @Override
    public Long getStoreIdByManagerId(Integer id) {
        Store store = this.findByManagerId(id);
        return store.getStoreId();
    }

    @Override
    public Boolean storeUpdate(Store store) {
        // 设置更新条件
        QueryWrapper<Store> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("manager_id", store.getManagerId());

        // 执行更新操作
        int affectedRows = storeMapper.update(store, queryWrapper);
        return affectedRows != 0;
    }

    @Override
    public Boolean storeDelete(Integer id) {
        QueryWrapper<Store> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("manager_id",id);
        int isDeleted  = storeMapper.delete(queryWrapper);
        return isDeleted != 0;
    }

    @Override
    public Boolean storeIsApprove(Long storeId) {
        QueryWrapper<Store> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("store_id",storeId);
        return storeMapper.selectOne(queryWrapper).getIsApproved();
    }

    @Override
    public PageVO listAll(int pageNum, int pageSize) {
        QueryWrapper<Store> queryWrapper = new QueryWrapper<>();
        Page<Store> page = new Page<>(pageNum,pageSize);
        Page<Store> resultPage = this.storeMapper.selectPage(page,queryWrapper);
        List<Store> storeList = resultPage.getRecords();

        PageVO pageVO = new PageVO();
        pageVO.setData(storeList);
        pageVO.setTotal(resultPage.getTotal());
        System.out.println(resultPage.getTotal());
        return pageVO;
    }

    @Override
    public PageVO unApprovedStoresList(int pageNum, int pageSize) {
        QueryWrapper<Store> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_approved",false);
        Page<Store> page = new Page<>(pageNum,pageSize);
        Page<Store> resultPage = this.storeMapper.selectPage(page,queryWrapper);
        List<Store> storeList = resultPage.getRecords();

        PageVO pageVO = new PageVO();
        pageVO.setData(storeList);
        pageVO.setTotal(resultPage.getTotal());
//        System.out.println(resultPage.getTotal());
        return pageVO;
    }

    @Override
    public PageVO isApprovedStoresList(int pageNum, int pageSize) {
        QueryWrapper<Store> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_approved",true);
        Page<Store> page = new Page<>(pageNum,pageSize);
        Page<Store> resultPage = this.storeMapper.selectPage(page,queryWrapper);
        List<Store> storeList = resultPage.getRecords();

        PageVO pageVO = new PageVO();
        pageVO.setData(storeList);
        pageVO.setTotal(resultPage.getTotal());
//        System.out.println(resultPage.getTotal());
        return pageVO;
    }

}
