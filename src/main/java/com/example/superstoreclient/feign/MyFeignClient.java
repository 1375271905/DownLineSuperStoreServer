package com.example.superstoreclient.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "SuperStoreSystem",url = "http://127.0.0.1:8000/") // 指定目标微服务的名称
public interface MyFeignClient {
    @GetMapping("/{id}")
    Integer getResourceById(@PathVariable("id") Long id);
}