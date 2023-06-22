package com.ecommerce.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecommerce.model.City;
import com.ecommerce.model.Customer;
import com.ecommerce.service.CityService;
import com.ecommerce.service.CustomerService;

@Controller
public class AccountController {
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	CityService cityService;
	
	@GetMapping("/account")
	public String accountHome(Model model, Principal principal) {
		
		if(principal == null) {
			
			return "redirect:/login";
		}
		
		String username = principal.getName();
		Customer customer = customerService.findByUsername(username);
		List<City> cityList = cityService.findAll();
		model.addAttribute("customer",customer);
		model.addAttribute("cities",cityList);
		
		
		return "account";
	}

}
