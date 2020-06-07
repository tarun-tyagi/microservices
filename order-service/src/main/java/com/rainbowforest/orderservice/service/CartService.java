package com.rainbowforest.orderservice.service;

import com.rainbowforest.orderservice.model.Item;
import java.util.List;

public interface CartService {

    public void addItemToCart(Long userId, Long productId, Integer quantity);
    public List<Item> getCart(Long userId);
    public void changeItemQuantity(Long userId, Long productId, Integer quantity);
    public void deleteItemFromCart(Long userId, Long productId);
    public boolean checkIfItemIsExist(Long userId, Long productId);
    public List<Item> getAllItemsFromCart(Long userId);
    public void deleteCart(Long userId);
}
