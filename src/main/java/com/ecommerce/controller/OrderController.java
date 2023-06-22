package com.ecommerce.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecommerce.model.Customer;
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
		
		if(customer.getPhoneNumber()==null && customer.getAddress()==null && customer.getCity() == null && customer.getCountry()==null) {
			
			return "account";
			
		}
		return "checkout";
	}

}
