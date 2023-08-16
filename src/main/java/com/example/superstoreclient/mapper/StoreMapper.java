package com.example.superstoreclient.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.superstoreclient.entity.Store;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StoreMapper extends BaseMapper<Store> {
}

