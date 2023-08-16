package com.example.superstoreclient.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.superstoreclient.entity.Store;
import com.example.superstoreclient.mapper.StoreMapper;
import com.example.superstoreclient.service.StoreService;
import com.example.superstoreclient.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
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

}
