package com.example.superstoreclient.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.superstoreclient.entity.Store;
import com.example.superstoreclient.vo.PageVO;

public interface StoreService extends IService<Store> {
    Boolean storeSave(Store store);
    Store findByManagerId(Integer id);
    Long getStoreIdByManagerId(Integer id);
    Boolean storeUpdate(Store store);
    Boolean storeDelete(Integer id);
    Boolean storeIsApprove(Long id);
    PageVO listAll(int pageNum, int pageSize);

    PageVO unApprovedStoresList (int pageNum, int pageSize);

    PageVO isApprovedStoresList (int pageNum, int pageSize);

}
