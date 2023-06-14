package com.ecommerce.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.model.CartItem;
import com.ecommerce.model.Customer;
import com.ecommerce.model.Product;
import com.ecommerce.model.ShoppingCart;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.ShoppingCartRepository;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Service
public class ShoppingCartService {
    
    private CartItemRepository itemRepository;
    private ShoppingCartRepository cartRepository;

    
    public ShoppingCart addItemToCart(Product product, int quantity, Customer customer) {
        // Retrieve the shopping cart for the customer
        ShoppingCart cart = customer.getShoppingCart();

        if (cart == null) {
            // If the cart doesn't exist, create a new one
            cart = new ShoppingCart();
        }

        // Get the set of cart items from the cart
        Set<CartItem> cartItems = cart.getCartItem();

        // Find the cart item with the matching product ID
        CartItem cartItem = findCartItem(cartItems, product.getId());

        if (cartItems == null) {
            // If the cart items set is null, create a new set
            cartItems = new HashSet<>();

            if (cartItem == null) {
                // If the item doesn't exist in the cart, create a new one
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setTotalPrice(quantity * product.getCostPrice());
                cartItem.setQuantity(quantity);
                cartItem.setCart(cart);
                cartItems.add(cartItem);
                itemRepository.save(cartItem);
            }
        } else {
            if (cartItem == null) {
                // If the item doesn't exist in the cart, create a new one
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setTotalPrice(quantity * product.getCostPrice());
                cartItem.setQuantity(quantity);
                cartItem.setCart(cart);
                cartItems.add(cartItem);
                itemRepository.save(cartItem);
            } else {
                // If the item already exists, update its quantity and total price
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItem.setTotalPrice(cartItem.getTotalPrice() + (quantity * product.getCostPrice()));
                itemRepository.save(cartItem);
            }
        }

        // Update the cart with the modified cart items, total items, total prices, and customer
        cart.setCartItem(cartItems);
        int totalItems = totalItems(cart.getCartItem());
        double totalPrice = totalPrice(cart.getCartItem());
        cart.setTotalItems(totalItems);
        cart.setTotalPrices(totalPrice);
        cart.setCustomer(customer);

        // Save the cart to the repository and return the updated cart
        return cartRepository.save(cart);
    }

   

    private CartItem findCartItem(Set<CartItem> cartItems, Long productId) {
        if (cartItems == null) {
            return null;
        }

        // Iterate over the cart items and find the one with the matching product ID
        for (CartItem item : cartItems) {
            if (item.getProduct().getId() == productId) {
                return item;
            }
        }
        return null;
    }

    private int totalItems(Set<CartItem> cartItems) {
        int totalItems = 0;

        // Sum up the quantities of all cart items
        for (CartItem item : cartItems) {
            totalItems += item.getQuantity();
        }
        return totalItems;
    }

    private double totalPrice(Set<CartItem> cartItems) {
        double totalPrice = 0.0;

        // Sum up the total prices of all cart items
        for (CartItem item : cartItems) {
            totalPrice += item.getTotalPrice();
        }
        return totalPrice;
    }
}
