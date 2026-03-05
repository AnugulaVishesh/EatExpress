package com.alpha.EatExpress.Servicee;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.alpha.EatExpress.DTO.DelivaryPartnerDTO;
import com.alpha.EatExpress.Exception.DeliveryPartnerNotFoundException;
import com.alpha.EatExpress.ResponceStructure.ResponceStructure;
import com.alpha.EatExpress.entity.DelivaryPartner;
import com.alpha.EatExpress.entity.Order;
import com.alpha.EatExpress.repository.DelivaryPartnerRepo;
import com.alpha.EatExpress.repository.OrderRepo;

@Service
public class DeliveryPartnerService {
	
	@Autowired 
	private DelivaryPartnerRepo delivarypartnerrepo;
	
	public ResponceStructure<DelivaryPartner> saveDP(DelivaryPartnerDTO dpdto) {

	    DelivaryPartner dp = new DelivaryPartner();

	    dp.setName(dpdto.getName());
	    dp.setMob(dpdto.getMob());
	    dp.setMail(dpdto.getMail());
	    dp.setVehicileno(dpdto.getVechileno());

	    DelivaryPartner saved = delivarypartnerrepo.save(dp);

	    ResponceStructure<DelivaryPartner> response = new ResponceStructure<>();

	    response.setStatusCode(HttpStatus.CREATED.value());
	    response.setMessage("Delivery Partner Saved Successfully");
	    response.setData(saved);

	    return response;
	}

	public ResponceStructure<DelivaryPartner> deleteByMob(long mob) {

	    DelivaryPartner dp = delivarypartnerrepo.findByMob(mob)
	            .orElseThrow(() ->
	                    new DeliveryPartnerNotFoundException(
	                            "Delivery Partner with Mobile " + mob + " not found"));

	    delivarypartnerrepo.delete(dp);

	    ResponceStructure<DelivaryPartner> response = new ResponceStructure<>();
	    response.setStatusCode(HttpStatus.OK.value());
	    response.setMessage("Delivery Partner Deleted Successfully");
	    response.setData(dp);

	    return response;
	}
	
	public ResponceStructure<DelivaryPartner> findByMob(long mob) {

	    DelivaryPartner dp = delivarypartnerrepo.findByMob(mob)
	            .orElseThrow(() ->
	                    new DeliveryPartnerNotFoundException(
	                            "Delivery Partner with Mobile " + mob + " not found"));

	    ResponceStructure<DelivaryPartner> response = new ResponceStructure<>();
	    response.setStatusCode(HttpStatus.OK.value());
	    response.setMessage("Delivery Partner Found Successfully");
	    response.setData(dp);

	    return response;
	}
        
	@Autowired
	private OrderRepo orderRepo;
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	public boolean acceptorder(Integer orderid, Integer partnerid) {
        Order order = orderRepo.findById(orderid).orElseThrow(() -> new RuntimeException("Order not found"));
        DelivaryPartner deliveryPartner = delivarypartnerrepo.findById(partnerid).orElseThrow(()
                -> new RuntimeException("partner not found"));


        String lockKey = "order_lock" + orderid;
        Boolean locked = redisTemplate.opsForValue().setIfAbsent(lockKey, partnerid.toString());
        if (Boolean.TRUE.equals(locked)) {
            order.setDelivaryPartner(deliveryPartner);
            orderRepo.save(order);
            redisTemplate.delete("order:" + orderid);

            return true;
        }
        
        return false;
    }
	
	
	

	
	
    


}