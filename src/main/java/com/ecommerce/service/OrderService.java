package com.ecommerce.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.model.CartItem;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderDetail;
import com.ecommerce.model.ShoppingCart;
import com.ecommerce.repository.OrderDetailRepositoty;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ShoppingCartRepository;

@Service
public class OrderService {

	@Autowired
	private OrderDetailRepositoty orderDetailRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ShoppingCartRepository cartRepository;

	void saveOrder(ShoppingCart cart) {

		Order order = new Order();
		order.setOrderStatus("PENDING");
		order.setOrderDate(new Date());
		order.setCustomer(cart.getCustomer());
		order.setTotalPrice(cart.getTotalPrices());
		List<OrderDetail> orderDetailList = new ArrayList<>();

		for (CartItem item : cart.getCartItem()) {
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrder(order);
			orderDetail.setQuantity(item.getQuantity());
			orderDetail.setProduct(item.getProduct());
			orderDetail.setUnitPrice(item.getProduct().getCostPrice());
			orderDetailRepository.save(orderDetail);
			orderDetailList.add(orderDetail);

		}
		
		order.setOrderDetailList(orderDetailList);
		cart.setCartItem(new HashSet<>());
		cart.setTotalItems(0);
		cart.setTotalPrices(0);
		cartRepository.save(cart);
		orderRepository.save(order);
		

	}

}
