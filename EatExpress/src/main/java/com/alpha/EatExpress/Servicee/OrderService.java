package com.alpha.EatExpress.Servicee;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alpha.EatExpress.DTO.OrderNeedConsentDTO;
import com.alpha.EatExpress.ResponceStructure.ResponceStructure;
import com.alpha.EatExpress.entity.CartItem;
import com.alpha.EatExpress.entity.Customer;
import com.alpha.EatExpress.entity.Item;
import com.alpha.EatExpress.entity.Order;
import com.alpha.EatExpress.entity.Restaurant;
import com.alpha.EatExpress.repository.CustomerRepo;
import com.alpha.EatExpress.repository.OrderRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderService {

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private CustomerService customerService;

	public ResponseEntity<ResponceStructure<OrderNeedConsentDTO>> placeOrder(long mobno) {

		Customer customer = customerService.findByMobno(mobno).getBody().getData();

		List<CartItem> cart = customer.getCart();

		if (cart == null || cart.isEmpty()) {
			throw new RuntimeException("Cart is empty");
		}

		Order order = new Order();

		order.setCustomer(customer);
		order.setStatus("PENDING");
		order.setDate(LocalDate.now().toString());

		order.setDeliveryAddress(customer.getAddress().getStreet());

		double orderPrice = 0;

		List<Item> items = new ArrayList<>();
		Restaurant restaurant = customer.getItem().get(0).getRestaurant();

		for (CartItem ci : cart) {

			Item item = ci.getItem();

			orderPrice += item.getPrice() * ci.getQuantity();

			items.add(item);
		}

		order.setItems(items);

		order.setOrderPrice(BigDecimal.valueOf(orderPrice));

		// calculate the distance from api
		double distance = 3;
		order.setDistance(distance);

		int deliveryCharges = calculateDeliveryCharge(distance);

		int packagingFees = restaurant.getPackagingFee();
		int platformFees = 5;

		double tax = orderPrice * 0.05;

		double totalCost = orderPrice + deliveryCharges + packagingFees + platformFees + tax;

		order.setDeliveryCharges(BigDecimal.valueOf(deliveryCharges));
		order.setPackagingFees(BigDecimal.valueOf(packagingFees));
		order.setPlatformFees(BigDecimal.valueOf(platformFees));
		order.setTax(BigDecimal.valueOf(tax));
		order.setCost(BigDecimal.valueOf(totalCost));

		order.setOtp(generateOtp());

		Order savedOrder = orderRepo.save(order);

		cart.clear();
		customerRepo.save(customer);

		OrderNeedConsentDTO dto = buildDTO(savedOrder);

		ResponceStructure<OrderNeedConsentDTO> rs = new ResponceStructure<>();

		rs.setStatusCode(HttpStatus.CREATED.value());
		rs.setMessage("Order sent to restaurant for consent");
		rs.setData(dto);

		return new ResponseEntity<>(rs, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponceStructure<Order>> confirmOrder(int orderId) {

		Order order = orderRepo.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

		order.setStatus("CONFIRMED");
		Restaurant restaurant = order.getItems().get(0).getRestaurant();
		order.setRestaurant(restaurant);
		restaurant.getOrders().add(order);
//  save restaurant also
		orderRepo.save(order);

		ResponceStructure<Order> response = new ResponceStructure<>();

		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Order confirmed");
		response.setData(order);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	public ResponseEntity<ResponceStructure<Order>> cancelOrder(int orderId) {

		Order order = orderRepo.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

		order.setStatus("CANCELLED");

		orderRepo.save(order);

		ResponceStructure<Order> response = new ResponceStructure<>();

		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Order cancelled");
		response.setData(order);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	private OrderNeedConsentDTO buildDTO(Order order) {

		OrderNeedConsentDTO dto = new OrderNeedConsentDTO();

		dto.setOrderId(order.getId());
		dto.setCustomerName(order.getCustomer().getName());
		dto.setDeliveryAddress(order.getDeliveryAddress());

		dto.setOrderPrice(order.getOrderPrice());
		dto.setDeliveryCharges(order.getDeliveryCharges());
		dto.setPackagingFees(order.getPackagingFees());
		dto.setPlatformFees(order.getPlatformFees());
		dto.setTax(order.getTax());
		dto.setTotalCost(order.getCost());
		dto.setDistance(order.getDistance());

		List<String> items = new ArrayList<>();

		for (Item item : order.getItems()) {
			items.add(item.getName());
		}

		dto.setItems(items);

		return dto;
	}

	private int calculateDeliveryCharge(double distance) {

		if (distance <= 2)
			return 20;
		else if (distance <= 5)
			return 40;
		else if (distance <= 8)
			return 60;
		else
			return 80;
	}

	private int generateOtp() {
		return 1000 + (int) (Math.random() * 9000);
	}
}