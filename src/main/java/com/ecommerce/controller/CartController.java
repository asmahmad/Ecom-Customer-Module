package com.ecommerce.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {
	
	@GetMapping("/cart")
	public String cart(Principal principal) {
		if (principal == null) {

			return "redirect:/login";
		}

		return "cart";
	}
}