package com.alpha.EatExpress.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alpha.EatExpress.DTO.CartWithCouponsDTO;
import com.alpha.EatExpress.DTO.CustomerDTO;
import com.alpha.EatExpress.DTO.DistanceCalculation;
import com.alpha.EatExpress.DTO.OrderNeedConsentDTO;
import com.alpha.EatExpress.Exception.CartEmptyException;
import com.alpha.EatExpress.Exception.CouponExpiredException;
import com.alpha.EatExpress.Exception.CouponInvalidException;
import com.alpha.EatExpress.Exception.CouponLimitExceededException;
import com.alpha.EatExpress.Exception.CouponNotFoundException;
import com.alpha.EatExpress.Exception.CustomerNotFoundException;
import com.alpha.EatExpress.Exception.ItemNotFoundException;
import com.alpha.EatExpress.Exception.OrderNotFoundException;
import com.alpha.EatExpress.ResponceStructure.ResponceStructure;
import com.alpha.EatExpress.entity.CartItem;
import com.alpha.EatExpress.entity.Coupon;
import com.alpha.EatExpress.entity.CouponRedemption;
import com.alpha.EatExpress.entity.Customer;
import com.alpha.EatExpress.entity.Item;
import com.alpha.EatExpress.entity.Order;
import com.alpha.EatExpress.entity.Restaurant;
import com.alpha.EatExpress.repository.CouponRedemptionRepository;
import com.alpha.EatExpress.repository.CouponRepository;
import com.alpha.EatExpress.repository.CustomerRepository;
import com.alpha.EatExpress.repository.ItemRepository;
import com.alpha.EatExpress.repository.OrderRepository;
import com.alpha.EatExpress.repository.RestaurantRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private RestaurantRepository restaurantRepo;

    @Autowired
    private ItemRepository itemRepo;

    @Autowired
    private CouponRepository couponRepo;

    @Autowired
    private CouponRedemptionRepository couponRedemptionRepo;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private OrderRepository orderRepository;
    
    

    // Register Customer
    public ResponseEntity<ResponceStructure<Customer>> register(CustomerDTO dto){

        Customer customer = new Customer();

        customer.setName(dto.getName());
        customer.setMobno(dto.getMobno());
        customer.setMailid(dto.getMailid());
        customer.setGender(dto.getGender());

        Customer savedCustomer = customerRepo.save(customer);
        
        // 📧 1. Send mail to Customer
        emailService.sendMail(
                dto.getMailid(),
                "Registration Successful",
                "Hello " + dto.getName() + ", your registration is successful in EatExpress."
        );

        // 📧 2. Send mail to Admin (YOU)
        emailService.sendMail(
                "yourgmail@gmail.com",
                "New Customer Registered",
                "Customer Name: " + dto.getName() +
                "\nEmail: " + dto.getMailid() +
                "\nMobile: " + dto.getMobno()
        );

        ResponceStructure<Customer> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.CREATED.value());
        rs.setMessage("Customer Registered Successfully");
        rs.setData(savedCustomer);

        return new ResponseEntity<>(rs,HttpStatus.CREATED);
    }

    // Find Customer
    public ResponseEntity<ResponceStructure<Customer>> findCustomer(long mobno){

        Customer customer = customerRepo.findByMobno(mobno)
                .orElseThrow(() -> new CustomerNotFoundException());

        ResponceStructure<Customer> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Customer Found");
        rs.setData(customer);

        return new ResponseEntity<>(rs,HttpStatus.OK);
    }

    // Delete Customer
    public ResponseEntity<ResponceStructure<String>> deleteCustomer(long mobno){

        Customer customer = customerRepo.findByMobno(mobno)
                .orElseThrow(() -> new CustomerNotFoundException());

        customerRepo.delete(customer);

        ResponceStructure<String> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Customer Deleted Successfully");
        rs.setData("Deleted");

        return new ResponseEntity<>(rs,HttpStatus.OK);
    }

    // Add To Cart
    public ResponseEntity<ResponceStructure<String>> addToCart(long mobno,int itemid,int quantity){

        Customer customer = customerRepo.findByMobno(mobno)
                .orElseThrow(() -> new CustomerNotFoundException());

        Item item = itemRepo.findById(itemid)
                .orElseThrow(() -> new ItemNotFoundException("Item not found"));

        if(customer.getCart()==null){
            customer.setCart(new ArrayList<>());
        }

        CartItem cartItem = new CartItem();
        cartItem.setItem(item);
        cartItem.setQuantity(quantity);

        customer.getCart().add(cartItem);

        customerRepo.save(customer);

        ResponceStructure<String> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Item Added To Cart");
        rs.setData("Success");

        return new ResponseEntity<>(rs,HttpStatus.OK);
    }

    // Get Cart
    public ResponseEntity<ResponceStructure<CartWithCouponsDTO>> getCart(long mobno){

        Customer customer = customerRepo.findByMobno(mobno)
                .orElseThrow(() -> new CustomerNotFoundException());

        List<CartItem> cart = customer.getCart();

        double total = 0;

        if(cart!=null){
            for(CartItem c : cart){
                total += c.getItem().getPrice() * c.getQuantity();
            }
        }

        List<Coupon> allCoupons = couponRepo.findByStatus("ACTIVE");

        List<Coupon> coupons = new ArrayList<>();

        for(Coupon c : allCoupons){

            boolean used = couponRedemptionRepo
                    .findByCouponAndCustomer(c, customer)
                    .isPresent();

            if(!used &&
               !c.getExpiryDate().isBefore(LocalDate.now()) &&
               c.getMaxCoupons() > 0 &&
               total >= c.getMinOrderPrice()){

                coupons.add(c);
            }
        }

        CartWithCouponsDTO dto = new CartWithCouponsDTO();

        dto.setCartItems(cart);
        dto.setCartTotal(total);
        dto.setCoupons(coupons);

        ResponceStructure<CartWithCouponsDTO> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Cart fetched successfully");
        rs.setData(dto);

        return new ResponseEntity<>(rs,HttpStatus.OK);
    }

    // Place Order
    public ResponseEntity<ResponceStructure<OrderNeedConsentDTO>> placeOrder(
            long mobno,
            String paymentType,
            String addressType,
            String specialRequest,
            Integer couponId) {

        // 1. Get Customer
        Customer customer = customerRepo.findByMobno(mobno)
                .orElseThrow(() -> new CustomerNotFoundException());

        // 2. Check Cart
        if (customer.getCart() == null || customer.getCart().isEmpty()) {
            throw new CartEmptyException("Cart is empty");
        }

        // 3. Get Restaurant from first cart item
        Restaurant restaurant = customer.getCart().get(0).getItem().getRestaurant();

        // ✅ IMPORTANT NULL CHECK (FIXED ERROR)
        if (restaurant == null) {
            throw new RuntimeException("Restaurant not found for this order. Please check item mapping.");
        }

        // 4. Calculate Item Cost
        double itemCost = 0;
        for (CartItem ci : customer.getCart()) {

            // Safety check
            if (ci.getItem() == null) {
                throw new RuntimeException("Item missing in cart");
            }

            if (ci.getItem().getRestaurant() == null) {
                throw new RuntimeException("Item is not linked to any restaurant");
            }

            itemCost += ci.getItem().getPrice() * ci.getQuantity();
        }

        // 5. Charges Calculation
        double packagingFees = restaurant.getPackagingFee();
        double platformFees = 5;
        double tax = itemCost * 0.05;

        double distance = DistanceCalculation.calculateDistance(
                restaurant.getAddress().getLatitude(),
                restaurant.getAddress().getLongitude(),
                customer.getAddress().getLatitude(),
                customer.getAddress().getLongitude()
        );

        double deliveryCharges = 0;
        if (distance > 2) {
            deliveryCharges = (distance - 2) * 10;
        }

        double totalCost = itemCost + packagingFees + platformFees + tax + deliveryCharges;

        double discount = 0;

        // 6. Coupon Logic
        if (couponId != null) {

            Coupon coupon = couponRepo.findById(couponId)
                    .orElseThrow(() -> new CouponNotFoundException());

            if (LocalDate.now().isAfter(coupon.getExpiryDate())) {
                throw new CouponExpiredException("Coupon expired");
            }

            if (totalCost < coupon.getMinOrderPrice()) {
                throw new CouponInvalidException("Minimum order price not satisfied");
            }

            if (coupon.getMaxCoupons() <= 0) {
                throw new CouponLimitExceededException("Coupon limit reached");
            }

            Optional<CouponRedemption> redemption =
                    couponRedemptionRepo.findByCouponAndCustomer(coupon, customer);

            if (redemption.isPresent()) {
                throw new CouponInvalidException("Coupon already used");
            }

            discount = totalCost * coupon.getOffer() / 100;

            if (discount > coupon.getMaxRedeemPrice()) {
                discount = coupon.getMaxRedeemPrice();
            }

            totalCost = totalCost - discount;
        }

        // 7. Create Order
        Order order = new Order();

        order.setCustomer(customer);
        order.setRestaurant(restaurant);
        order.setSpecialRequest(specialRequest);
        order.setStatus("WAITING_FOR_CONSENT");

        order.setOrderPrice(BigDecimal.valueOf(itemCost));
        order.setDiscountamount(BigDecimal.valueOf(discount));
        order.setFinalAmount(BigDecimal.valueOf(totalCost));

        // 8. Save Order
        Order savedOrder = orderRepo.save(order);

        // 9. Response DTO
        OrderNeedConsentDTO dto = new OrderNeedConsentDTO();

        dto.setOrderId(savedOrder.getId());
        dto.setRestaurantName(restaurant.getName());
        dto.setItemCost(itemCost);
        dto.setPackagingFees(packagingFees);
        dto.setPlatformFees(platformFees);
        dto.setTax(tax);
        dto.setDeliveryCharges(deliveryCharges);
        dto.setDistance(distance);
        dto.setTotalCost(totalCost);

        // 10. Response Structure
        ResponceStructure<OrderNeedConsentDTO> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.CREATED.value());
        rs.setMessage("Order created - waiting for customer consent");
        rs.setData(dto);

        return new ResponseEntity<>(rs, HttpStatus.CREATED);
    }
    public ResponseEntity<ResponceStructure<String>> confirmPlacingOrderByCod(int orderid) {

    	Order order = orderRepository.findById(orderid)
    	        .orElseThrow(() -> new RuntimeException("Order not found"));

    	Order savedOrder = orderRepository.save(order);

        // ================== 📧 EMAIL LOGIC ==================

        String customerName = savedOrder.getCustomer().getName();
        String customerEmail = savedOrder.getCustomer().getMailid();

        // Convert items list
        String items = savedOrder.getItems()
                .stream()
                .map(item -> item.getName())
                .reduce((a, b) -> a + ", " + b)
                .orElse("No items");

        String subject = "Order Confirmed - EatExpress";

        String body = "Hello " + customerName + ",\n\n"
                + "Your order has been CONFIRMED ✅\n\n"
                + "Order ID: " + savedOrder.getId() + "\n"
                + "Restaurant: " + savedOrder.getRestaurant().getName() + "\n"
                + "Items: " + items + "\n"
                + "Delivery Address: " + savedOrder.getDeliveryAddress() + "\n"
                + "Final Amount: ₹" + savedOrder.getFinalAmount() + "\n\n"
                + "Estimated Time: " + savedOrder.getEstimatedTime() + "\n\n"
                + "Thank you for ordering with EatExpress!";

        // 🔥 Send Mail
        emailService.sendMail(customerEmail, subject, body);

        // ================== END EMAIL ==================

        ResponceStructure<String> response = new ResponceStructure<>();
        response.setStatusCode(200);
        response.setMessage("Order confirmed successfully");
        response.setData("Email sent to customer");

        return ResponseEntity.ok(response);
    }

    // Cancel Order
    public ResponseEntity<ResponceStructure<Order>> cancelOrder(long phone, Long orderId){

        Customer customer = customerRepo.findByMobno(phone)
                .orElseThrow(() -> new CustomerNotFoundException());

        Order order = orderRepo.findById(orderId.intValue())
                .orElseThrow(() -> new OrderNotFoundException());

        order.setStatus("CANCELLED");

        orderRepo.save(order);

        ResponceStructure<Order> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Order Cancelled Successfully");
        rs.setData(order);

        return new ResponseEntity<>(rs,HttpStatus.OK);
    }

    // Search Restaurant or Item
    public ResponseEntity<ResponceStructure<List<Restaurant>>> searchItemOrRestaurant(long mobno,String searchkey){

        Customer customer = customerRepo.findByMobno(mobno)
                .orElseThrow(() -> new CustomerNotFoundException());

        String city = customer.getAddress().getCity();

        List<Restaurant> restaurants = restaurantRepo.findByAddress_City(city);

        List<Restaurant> result = restaurants.stream()
                .filter(r ->
                        r.getName().toLowerCase().contains(searchkey.toLowerCase())
                        ||
                        r.getMenuItems().stream()
                                .anyMatch(i -> i.getName().toLowerCase().contains(searchkey.toLowerCase()))
                )
                .toList();

        ResponceStructure<List<Restaurant>> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Search Results");
        rs.setData(result);

        return new ResponseEntity<>(rs,HttpStatus.OK);
    }

    // Remove Item From Cart
    public ResponseEntity<ResponceStructure<String>> removeItemFromCart(long customermobno,long restmob,int itemid){

        Customer customer = customerRepo.findByMobno(customermobno)
                .orElseThrow(() -> new CustomerNotFoundException());

        if(customer.getCart()==null || customer.getCart().isEmpty()){
            throw new CartEmptyException("Cart empty");
        }

        CartItem cartItem = customer.getCart().stream()
                .filter(ci -> ci.getItem().getId()==itemid &&
                        ci.getItem().getRestaurant().getMobno()==restmob)
                .findFirst()
                .orElseThrow(() -> new ItemNotFoundException("Item not found in cart"));

        customer.getCart().remove(cartItem);

        customerRepo.save(customer);

        ResponceStructure<String> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Item removed from cart successfully");
        rs.setData("Removed Item ID: "+itemid);

        return new ResponseEntity<>(rs,HttpStatus.OK);
    }

	public void regCutToValidateData(CustomerDTO cddto) {
		System.out.println(cddto );
		System.out.println("DATA IS VALID");
	}
}