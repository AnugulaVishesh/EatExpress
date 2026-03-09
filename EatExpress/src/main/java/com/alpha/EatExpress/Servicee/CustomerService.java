package com.alpha.EatExpress.Servicee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alpha.EatExpress.DTO.CartWithCouponsDTO;
import com.alpha.EatExpress.DTO.CustomerDTO;
import com.alpha.EatExpress.DTO.OrderNeedConsentDTO;
import com.alpha.EatExpress.Exception.CartEmptyException;
import com.alpha.EatExpress.Exception.CustomerNotFoundException;
import com.alpha.EatExpress.Exception.ItemNotFoundException;
import com.alpha.EatExpress.Exception.OrderNotFoundException;
import com.alpha.EatExpress.ResponceStructure.ResponceStructure;
import com.alpha.EatExpress.entity.CartItem;
import com.alpha.EatExpress.entity.Customer;
import com.alpha.EatExpress.entity.Item;
import com.alpha.EatExpress.entity.Order;
import com.alpha.EatExpress.entity.Restaurant;
import com.alpha.EatExpress.repository.CustomerRepository;
import com.alpha.EatExpress.repository.ItemRepository;
import com.alpha.EatExpress.repository.OrderRepository;
import com.alpha.EatExpress.repository.RestaurantRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public ResponseEntity<ResponceStructure<Customer>> register(CustomerDTO dto){

        Customer customer = new Customer();

        customer.setName(dto.getName());
        customer.setMobno(dto.getMobno());
        customer.setMailid(dto.getMailid());
        customer.setGender(dto.getGender());

        Customer savedCustomer = customerRepository.save(customer);

        ResponceStructure<Customer> rs = new ResponceStructure<>();

        rs.setStatusCode(HttpStatus.CREATED.value());
        rs.setMessage("Customer Registered Successfully");
        rs.setData(savedCustomer);

        return new ResponseEntity<>(rs,HttpStatus.CREATED);
    }

    public ResponseEntity<ResponceStructure<Customer>> findCustomer(long mobno){

        Customer customer = customerRepository.findByMobno(mobno)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        ResponceStructure<Customer> rs = new ResponceStructure<>();

        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Customer Found");
        rs.setData(customer);

        return new ResponseEntity<>(rs,HttpStatus.OK);
    }

    public ResponseEntity<ResponceStructure<String>> deleteCustomer(long mobno){

        Customer customer = customerRepository.findByMobno(mobno)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        customerRepository.delete(customer);

        ResponceStructure<String> rs = new ResponceStructure<>();

        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Customer Deleted Successfully");
        rs.setData("Deleted");

        return new ResponseEntity<>(rs,HttpStatus.OK);
    }

    public ResponseEntity<ResponceStructure<String>> addToCart(long mobno,int itemid,int quantity){

        Customer customer = customerRepository.findByMobno(mobno)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        Item item = itemRepository.findById(itemid)
                .orElseThrow(() -> new ItemNotFoundException("Item not found"));

        CartItem cartItem = new CartItem();

        cartItem.setItem(item);
        cartItem.setQuantity(quantity);

        customer.getCart().add(cartItem);

        customerRepository.save(customer);

        ResponceStructure<String> rs = new ResponceStructure<>();

        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Item Added To Cart");
        rs.setData("Success");

        return new ResponseEntity<>(rs,HttpStatus.OK);
    }

    public ResponseEntity<ResponceStructure<CartWithCouponsDTO>> getCart(long mobno){

        Customer customer = customerRepository.findByMobno(mobno)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        CartWithCouponsDTO dto = new CartWithCouponsDTO();

        dto.setCartItems(customer.getCart());

        double total = 0;

        for(CartItem c : customer.getCart()){
            total += c.getItem().getPrice() * c.getQuantity();
        }

        dto.setCartTotal(total);

        ResponceStructure<CartWithCouponsDTO> rs = new ResponceStructure<>();

        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Cart fetched successfully");
        rs.setData(dto);

        return new ResponseEntity<>(rs,HttpStatus.OK);
    }

    public ResponseEntity<ResponceStructure<OrderNeedConsentDTO>> placeOrder(
            long mobno,String paymentType,String addressType,String specialRequest,Integer couponId){

        Customer customer = customerRepository.findByMobno(mobno)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        if(customer.getCart()==null || customer.getCart().isEmpty()){
            throw new CartEmptyException("Cart is empty");
        }

        Order order = new Order();

        order.setCustomer(customer);
        order.setStatus("WAITING_FOR_CONSENT");

        Order savedOrder = orderRepository.save(order);

        OrderNeedConsentDTO dto = new OrderNeedConsentDTO();

        dto.setOrderId(savedOrder.getId());
        dto.setCustomerName(customer.getName());

        ResponceStructure<OrderNeedConsentDTO> rs = new ResponceStructure<>();

        rs.setStatusCode(HttpStatus.CREATED.value());
        rs.setMessage("Order created - waiting for customer consent");
        rs.setData(dto);

        return new ResponseEntity<>(rs,HttpStatus.CREATED);
    }

    public ResponseEntity<ResponceStructure<String>> confirmPlacingOrder(int orderid){

        Order order = orderRepository.findById(orderid)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        order.setStatus("PLACED");

        orderRepository.save(order);

        ResponceStructure<String> rs = new ResponceStructure<>();

        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Order Confirmed Successfully");
        rs.setData("Order placed successfully");

        return new ResponseEntity<>(rs,HttpStatus.OK);
    }

    public ResponseEntity<ResponceStructure<String>> denyPlacingOrder(int orderid){

        Order order = orderRepository.findById(orderid)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        order.setStatus("CANCELLED");

        orderRepository.save(order);

        ResponceStructure<String> rs = new ResponceStructure<>();

        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Order Cancelled");
        rs.setData("Cancelled");

        return new ResponseEntity<>(rs,HttpStatus.OK);
    }

    public ResponseEntity<ResponceStructure<List<Restaurant>>> searchItemOrRestaurant(long mobno,String searchkey){

        List<Restaurant> restaurants = restaurantRepository.findAll();

        List<Restaurant> result = restaurants.stream()
                .filter(r -> r.getName().toLowerCase().contains(searchkey.toLowerCase()))
                .toList();

        ResponceStructure<List<Restaurant>> rs = new ResponceStructure<>();

        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Search Results");
        rs.setData(result);

        return new ResponseEntity<>(rs,HttpStatus.OK);
    }

    public ResponseEntity<ResponceStructure<String>> removeItemFromCart(long customermobno, long restmob, int itemid) {

        Customer customer = customerRepository.findByMobno(customermobno)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        if(customer.getCart() == null || customer.getCart().isEmpty()){
            throw new CartEmptyException("Cart is empty");
        }

        CartItem cartItem = customer.getCart().stream()
                .filter(ci -> ci.getItem().getId() == itemid
                        && ci.getItem().getRestaurant().getMobno() == restmob)
                .findFirst()
                .orElseThrow(() -> new ItemNotFoundException("Item not found in cart"));

        customer.getCart().remove(cartItem);

        customerRepository.save(customer);

        ResponceStructure<String> rs = new ResponceStructure<>();

        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Item removed from cart successfully");
        rs.setData("Removed Item ID: " + itemid);

        return new ResponseEntity<>(rs, HttpStatus.OK);
    }
}