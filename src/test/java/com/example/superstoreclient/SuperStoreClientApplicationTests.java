package com.example.superstoreclient;

import com.example.superstoreclient.controller.StoreController;
import com.example.superstoreclient.vo.ResultVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SuperStoreClientApplicationTests {
    @Autowired
    private StoreController storeController;

    @Test
    void testFindById(){
        ResultVO store = storeController.findByManagerId(1);
        System.out.println(store);
    }

    // Add more test methods as needed...
}