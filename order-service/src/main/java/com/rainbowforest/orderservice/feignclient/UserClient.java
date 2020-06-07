package com.rainbowforest.orderservice.feignclient;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.rainbowforest.orderservice.model.User;

@FeignClient(name="user-service")
@RibbonClient(name="user-service")
public interface UserClient {

    @GetMapping(value = "/users/{id}")
    public User getOneUser(@PathVariable("id") Long id);
}
