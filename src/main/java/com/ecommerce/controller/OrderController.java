package com.ecommerce.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecommerce.model.Customer;
import com.ecommerce.model.ShoppingCart;
import com.ecommerce.service.CustomerService;

@Controller
public class OrderController {
	
	@Autowired
	CustomerService customerService;
	
	@GetMapping("/check-out")
	public String checkout(Model model, Principal principal) {
		if (principal == null) {
			
			return "redirect:/login";
		}
		String username = principal.getName();
		Customer customer = customerService.findByUsername(username);
		
		if(customer.getPhoneNumber().trim().isEmpty() || customer.getAddress().trim().isEmpty() || customer.getCity().trim().isEmpty() || customer.getCountry().trim().isEmpty()) {
			
			model.addAttribute("customer",customer);
			model.addAttribute("error","You must fill all the information");
			return "account";
			
		}else {
			
			model.addAttribute("customer",customer);
			ShoppingCart cart = customer.getShoppingCart();
			model.addAttribute("cart",cart);
			
		}
		
		return "checkout";
	}
	
	@GetMapping("/order")
	public String placeOrder() {
		
		return "order";
	}

}
