package com.alpha.EatExpress.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.alpha.EatExpress.Entity.Customer;
import com.alpha.EatExpress.Entity.Deliverypartner;
import com.alpha.EatExpress.Entity.ResponseStructure;
import com.alpha.EatExpress.Entity.Restaurant;


@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(RestaurantNotFound.class)
	public ResponseEntity<ResponseStructure<Restaurant>> handleRestaurantNotFoundException(){
		ResponseStructure<Restaurant> rs= new ResponseStructure<Restaurant>();
		rs.setStatuscode(400);
		rs.setMessage("Restaurant Not Found");
		rs.setData(null);
		return new ResponseEntity<ResponseStructure<Restaurant>>(rs,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DeliveryPartnerNotFound.class)
	public ResponseEntity<ResponseStructure<Deliverypartner>> handleDeliveryPartnerNotFoundException() {
	
		ResponseStructure<Deliverypartner> rs=new ResponseStructure<Deliverypartner>();
		
		rs.setStatuscode(400);
		rs.setMessage("DeliveryPartner Not Found");
		rs.setData(null);
		return new ResponseEntity<ResponseStructure<Deliverypartner>>(rs,HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(CustomerNotFound.class)
	public ResponseEntity<ResponseStructure<Customer>> handleCustomerNotFoundException() {
		
		ResponseStructure<Customer> rs=new ResponseStructure<Customer>();
		
		rs.setStatuscode(400);
		rs.setMessage("Customer Not Found");
		rs.setData(null);
		return new ResponseEntity<ResponseStructure<Customer>>(rs,HttpStatus.NOT_FOUND);
		
	}

}
	
	