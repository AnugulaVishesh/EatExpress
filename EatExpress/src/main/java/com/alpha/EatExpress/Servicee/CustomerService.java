package com.alpha.EatExpress.Servicee;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.alpha.EatExpress.DTO.CustomerDto;
import com.alpha.EatExpress.Exception.CustomerNotFoundException;
import com.alpha.EatExpress.Exception.ItemNotFoundException;
import com.alpha.EatExpress.Exception.RestaurantNotFoundException;
import com.alpha.EatExpress.ResponceStructure.ResponceStructure;
import com.alpha.EatExpress.entity.Address;
import com.alpha.EatExpress.entity.CartItem;
import com.alpha.EatExpress.entity.Customer;
import com.alpha.EatExpress.entity.Item;
import com.alpha.EatExpress.entity.Order;
import com.alpha.EatExpress.entity.Restaurant;
import com.alpha.EatExpress.repository.CustomerRepo;
import com.alpha.EatExpress.repository.ItemRepo;
import com.alpha.EatExpress.repository.OrderRepo;
import com.alpha.EatExpress.repository.RestaurantRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepo customerrepo;

    @Autowired
    private RestaurantRepo restaurantrepo;

    @Autowired
    private ItemRepo itemrepo;

    @Autowired
    private OrderRepo orderRepo;

    public ResponseEntity<ResponceStructure<Customer>> saveCustomer(CustomerDto cdto) {

        Customer customer = new Customer();
        customer.setName(cdto.getName());
        customer.setMobno(cdto.getMobno());
        customer.setMailid(cdto.getMailid());
        customer.setGender(cdto.getGender());

        Customer saved = customerrepo.save(customer);

        ResponceStructure<Customer> response = new ResponceStructure<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Customer Saved Successfully");
        response.setData(saved);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<ResponceStructure<Customer>> findByMobno(long mobno) {

        Customer customer = customerrepo.findByMobno(mobno)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with mobile number: " + mobno));

        ResponceStructure<Customer> response = new ResponceStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Customer Found Successfully");
        response.setData(customer);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<ResponceStructure<String>> deleteByMobno(long mobno) {

        Customer customer = findByMobno(mobno).getBody().getData();

        customerrepo.deleteByMobno(mobno);

        ResponceStructure<String> response = new ResponceStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Customer Deleted Successfully");
        response.setData("Deleted customer with mobile number: " + mobno);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    public ResponseEntity<ResponceStructure<Customer>> addAddress(long mobno, Address address) {

        Customer customer = customerrepo.findByMobno(mobno)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        customer.setAddress(address);

        customerrepo.save(customer);

        ResponceStructure<Customer> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Address added successfully");
        rs.setData(customer);

        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    public ResponseEntity<ResponceStructure<List<Restaurant>>> searchItemOrRestaurant(long mobno, String searchkey) {

        Customer customer = findByMobno(mobno).getBody().getData();

        if (customer.getAddress() == null) {
            throw new RuntimeException("Customer address not found. Please add address first.");
        }

        String city = customer.getAddress().getCity();

        List<Restaurant> restaurantsInCity = restaurantrepo.findByAddress_City(city);

        List<Restaurant> filteredRestaurants = restaurantsInCity.stream()
                .filter(r -> r.getName().toLowerCase().contains(searchkey.toLowerCase())
                        || r.getMenuItems().stream()
                        .anyMatch(i -> i.getName().toLowerCase().contains(searchkey.toLowerCase())))
                .toList();

        ResponceStructure<List<Restaurant>> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Search completed successfully");
        rs.setData(filteredRestaurants);

        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    public ResponseEntity<ResponceStructure<String>> addToCart(long mobno, int itemId, int quantity) {

        Customer customer = findByMobno(mobno).getBody().getData();

        Item item = itemrepo.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("Item not found with id: " + itemId));

        if (customer.getCart() == null || customer.getCart().size() == 0) {

            if (customer.getCart() == null) {
                customer.setCart(new ArrayList<>());
            }

            CartItem cartItem = new CartItem();
            cartItem.setItem(item);
            cartItem.setQuantity(quantity);
            customer.getCart().add(cartItem);

        } else {

            if (item.getRestaurant().getId() == customer.getCart().get(0).getItem().getRestaurant().getId()) {

                CartItem cartItem = new CartItem();
                cartItem.setItem(item);
                cartItem.setQuantity(quantity);
                customer.getCart().add(cartItem);

            } else {

                restaurantrepo.findById(item.getRestaurant().getId())
                        .filter(r -> r.getId() == customer.getCart().get(0).getItem().getRestaurant().getId())
                        .orElseThrow(() -> new RestaurantNotFoundException(
                                "You cannot add items from different restaurants to the same cart"));

                CartItem cartItem = new CartItem();
                cartItem.setItem(item);
                cartItem.setQuantity(quantity);
                customer.getCart().add(cartItem);
            }
        }

        customerrepo.save(customer);

        ResponceStructure<String> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Item Added To Cart");
        rs.setData("Added successfully");

        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    public void acceptPlacingOrderGiveConcent(int orderid) {

        Order ordertobeconfirmed = orderRepo.findById(orderid)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        ordertobeconfirmed.setStatus("CONFIRMED");

        orderRepo.save(ordertobeconfirmed);
    }
}