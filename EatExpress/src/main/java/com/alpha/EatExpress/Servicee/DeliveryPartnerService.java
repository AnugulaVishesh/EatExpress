package com.alpha.EatExpress.Servicee;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.alpha.EatExpress.DTO.DelivaryPartnerDTO;
import com.alpha.EatExpress.Exception.DeliveryPartnerLocationNotFoundException;
import com.alpha.EatExpress.Exception.DeliveryPartnerNotFoundException;
import com.alpha.EatExpress.Exception.OrderNotFoundException;
import com.alpha.EatExpress.ResponceStructure.ResponceStructure;
import com.alpha.EatExpress.entity.DeliveryPartner;
import com.alpha.EatExpress.entity.Order;
import com.alpha.EatExpress.repository.DeliveryPartnerRepository;
import com.alpha.EatExpress.repository.OrderRepository;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class DeliveryPartnerService {

    @Autowired
    private DeliveryPartnerRepository deliveryPartnerRepo;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public ResponseEntity<ResponceStructure<DeliveryPartner>> register(DelivaryPartnerDTO ddto) {

        DeliveryPartner dp = new DeliveryPartner();

        dp.setName(ddto.getName());
        dp.setMob(ddto.getMob());
        dp.setMail(ddto.getMail());
        dp.setVehicileno(ddto.getVechileno());
        dp.setStatus("AVAILABLE");
        dp.setRating(0);

        DeliveryPartner saved = deliveryPartnerRepo.save(dp);

        ResponceStructure<DeliveryPartner> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.CREATED.value());
        rs.setMessage("Delivery Partner Registered Successfully");
        rs.setData(saved);

        return new ResponseEntity<>(rs, HttpStatus.CREATED);
    }

    public ResponseEntity<ResponceStructure<DeliveryPartner>> find(long mob) {

        DeliveryPartner partner = deliveryPartnerRepo.findByMob(mob)
                .orElseThrow(() -> new DeliveryPartnerNotFoundException("Delivery Partner not found"));

        ResponceStructure<DeliveryPartner> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Delivery Partner Found");
        rs.setData(partner);

        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    public ResponseEntity<ResponceStructure<String>> delete(long mob) {

        DeliveryPartner partner = deliveryPartnerRepo.findByMob(mob)
                .orElseThrow(() -> new DeliveryPartnerNotFoundException("Delivery Partner not found"));

        deliveryPartnerRepo.delete(partner);

        ResponceStructure<String> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Delivery Partner Deleted");
        rs.setData("Deleted Successfully");

        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    public ResponseEntity<ResponceStructure<String>> updateDeliveryPartnerLocation(
            Integer partnerid,
            double latitude,
            double longitude) {

        String result = redisService.updateDpLoc(partnerid, latitude, longitude);

        ResponceStructure<String> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage(result);
        rs.setData(result);

        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    public ResponseEntity<ResponceStructure<String>> acceptOrder(Integer orderid, Integer partnerid) {

        Order order = orderRepo.findById(orderid)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        DeliveryPartner partner = deliveryPartnerRepo.findById(partnerid)
                .orElseThrow(() -> new DeliveryPartnerNotFoundException("Partner not found"));

        String lockKey = "order_lock:" + orderid;

        Boolean locked = redisTemplate.opsForValue().setIfAbsent(lockKey, partnerid.toString());

        String message;

        if (Boolean.TRUE.equals(locked)) {

            order.setDeliveryPartner(partner);

            if (partner.getOrders() == null) {
                partner.setOrders(new ArrayList<>());
            }

            partner.getOrders().add(order);

            order.setStatus("ASSIGNED");

            orderRepo.save(order);

            redisTemplate.delete("order:" + orderid);

            message = "Order assigned successfully";
        } else {
            message = "Order already taken by another partner";
        }

        ResponceStructure<String> rs = new ResponceStructure<>();
        rs.setStatusCode(HttpStatus.OK.value());
        rs.setMessage("Order Response");
        rs.setData(message);

        return new ResponseEntity<>(rs, HttpStatus.OK);
    }

    public void getDirectionToRestaurant(Integer partnerId,
                                         double restlat,
                                         double restlong,
                                         HttpServletResponse resp) throws IOException {

        String key = "deliverypartner:location";

        List<Point> points = redisTemplate.opsForGeo().position(key, partnerId.toString());

        if (points == null || points.isEmpty()) {
            throw new DeliveryPartnerLocationNotFoundException("Delivery Partner Location not found");
        }

        Point p = points.get(0);

        double dplon = p.getX();
        double dplat = p.getY();

        String url = "https://www.google.com/maps/dir/?api=1&origin="
                + dplat + "," + dplon
                + "&destination=" + restlat + "," + restlong
                + "&travelmode=driving";

        resp.sendRedirect(url);
    }

    public void getDirectionToCustomer(double restlat,
                                       double restlon,
                                       double custlat,
                                       double custlong,
                                       HttpServletResponse response) throws IOException {

        String url = "https://www.google.com/maps/dir/?api=1&origin="
                + restlat + "," + restlon
                + "&destination=" + custlat + "," + custlong
                + "&travelmode=driving";

        response.sendRedirect(url);
    }
}