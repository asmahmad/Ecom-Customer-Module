package com.ecommerce.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerce.model.Customer;
import com.ecommerce.model.Product;
import com.ecommerce.model.ShoppingCart;
import com.ecommerce.service.CustomerService;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.ShoppingCartService;

@Controller
public class CartController {
	
	@Autowired
	private ProductService productService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ShoppingCartService cartService;
	
	@GetMapping("/cart")
	public String cart(Principal principal, Model model) {
		if (principal == null) {

			return "redirect:/login";
		}
		String username = principal.getName();
		Customer customer = customerService.findByUsername(username);
		ShoppingCart shoppingCart = customer.getShoppingCart();
		if(shoppingCart == null) {
			model.addAttribute("check", "Cart is Empty");
		}
		model.addAttribute("shoppingCart", shoppingCart);

		return "cart";
	}
	
	@PostMapping("/add-to-cart")
	public String addItemToCart(@RequestParam("id") Long productId,
			@RequestParam(value="quantity", required=false, defaultValue="1") int quantity,
			Principal principal,
			HttpServletRequest request) {
		
		if(principal == null) {
			
			return "redirect:/login";
			
		}
		Product product = productService.getProductById(productId);
		String username = principal.getName();
		Customer customer = customerService.findByUsername(username);
		ShoppingCart cart = cartService.addItemToCart(product, quantity, customer);

		
		return "redirect:" + request.getHeader("Referer");
		
	}
}