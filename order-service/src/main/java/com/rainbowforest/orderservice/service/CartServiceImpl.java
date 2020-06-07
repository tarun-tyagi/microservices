package com.rainbowforest.orderservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rainbowforest.orderservice.feignclient.ProductClient;
import com.rainbowforest.orderservice.model.Item;
import com.rainbowforest.orderservice.model.Product;
import com.rainbowforest.orderservice.utilities.CartUtilities;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductClient productClient;

    Map<Long, List<Item>> cart=new HashMap<>();
    @Override
    public void addItemToCart(Long userId, Long productId, Integer quantity) {
        Product product = productClient.getProductById(productId);
        Item newItem = new Item(quantity,product, CartUtilities.getSubTotalForItem(product,quantity));
       
        if(cart.containsKey(userId)) {
        	List<Item> items=cart.get(userId);
        Optional<Item> oldItem=items.stream()
            .filter(item -> item.getProduct().getId().equals(productId))
            .findFirst();
        if(oldItem.isPresent()) {
        	items.remove(oldItem.get());
        }
        items.add(newItem);
        cart.put(userId, items);
        }else {
        	List<Item> list=new ArrayList<>();
        	list.add(newItem);
        	cart.put(userId, list);        	
        }
        
    }

    @Override
    public List<Item> getCart(Long userId) {
    	List<Item> list=cart.get(userId);
        return list!=null?list:new ArrayList<>();
    }

    public void changeItemQuantity(Long userId, Long productId, Integer quantity) {
        List<Item> cartItems = cart.get(userId);
        	Optional<Item> oldItem=cartItems.stream()
            	            .filter(item -> item.getProduct().getId().equals(productId))
            	            .findFirst();
            	        if(oldItem.isPresent()) {
            	        	Item val=oldItem.get();
            	        	cartItems.remove(val);
            	        	val.setQuantity(quantity);
                	        val.setSubTotal(CartUtilities.getSubTotalForItem(val.getProduct(),quantity));
                            cartItems.add(val);
                            cart.put(userId, cartItems);
            	        }
            	        
    }

    @Override
    public void deleteItemFromCart(Long userId, Long productId) {
    	List<Item> cartItems = cart.get(userId);
    	Optional<Item> oldItem=cartItems.stream()
        	            .filter(item -> item.getProduct().getId().equals(productId))
        	            .findFirst();
        	        if(oldItem.isPresent()) {
        	        	Item val=oldItem.get();
        	        	cartItems.remove(val);
        	        }
    }

    @Override
    public boolean checkIfItemIsExist(Long userId, Long productId) {
    	List<Item> cartItems = cart.get(userId);
    	Optional<Item> oldItem=cartItems.stream()
        	            .filter(item -> item.getProduct().getId().equals(productId))
        	            .findFirst();
        	        if(oldItem.isPresent()) {
        	        	return true;
        	        	        	        }
        	        else
        	        	return false;
    }

    @Override
    public List<Item> getAllItemsFromCart(Long userId) {
    	return cart.get(userId);
    }

    @Override
    public void deleteCart(Long userId) {
        cart.remove(userId);
    }
}
