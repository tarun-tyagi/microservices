package com.rainbowforest.orderservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rainbowforest.orderservice.model.Item;
import com.rainbowforest.orderservice.service.CartService;

@RestController
public class CartController {
	
	@Autowired
	CartService cartService;

    @GetMapping (value = "/cart/{userId}")
    private ResponseEntity<List<Item>> getCart(@PathVariable Long userId){
        List<Item> cart = cartService.getCart(userId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PostMapping(value = "/cart", params = {"productId", "quantity","userId"})
    private ResponseEntity addItemToCart(
            @RequestParam("productId") Long productId,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("userId") Long userId
            ) {
        List<Item> cart = cartService.getCart(userId);
        if(cart.isEmpty()){
            cartService.addItemToCart(userId, productId, quantity);
        }else{
            if(cartService.checkIfItemIsExist(userId, productId)){
                cartService.changeItemQuantity(userId, productId, quantity);
            }else {
                cartService.addItemToCart(userId, productId, quantity);
            }
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/cart", params = {"productId","userId"})
    private ResponseEntity removeItemFromCart(
            @RequestParam("productId") Long productId,
            @RequestParam("userId") Long userId){
        cartService.deleteItemFromCart(userId, productId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
