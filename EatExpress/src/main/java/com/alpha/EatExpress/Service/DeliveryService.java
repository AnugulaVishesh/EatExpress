package com.alpha.EatExpress.Service;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.alpha.EatExpress.DTO.DeliveryPartnerRegDto;
import com.alpha.EatExpress.Entity.Deliverypartner;
import com.alpha.EatExpress.Entity.ResponseStructure;
import com.alpha.EatExpress.Exception.DeliveryPartnerNotFound;
import com.alpha.EatExpress.Repository.DeliveryRepository;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    public String registerDeliveryPartner(DeliveryPartnerRegDto dto){

        Deliverypartner dp = new Deliverypartner();
        dp.setName(dto.getName());
        dp.setMobno(dto.getMobno());
        dp.setEmailid(dto.getEmailid());
        dp.setVechicalNo(dto.getVechicalNo());

        deliveryRepository.save(dp);

        return "Delivery Partner Registered Successfully";
    }
    
   

public ResponseStructure<Deliverypartner> findBymobno(long mob) {
ResponseStructure<Deliverypartner> rs= new ResponseStructure<Deliverypartner>();
		
		Deliverypartner d=deliveryRepository.findBymobno(mob).orElseThrow(() -> new DeliveryPartnerNotFound() );
		 rs.setStatuscode(200);
		    rs.setMessage("Delivery partner found successfully");
		    rs.setData(d);
		    return rs;
		
		
	}



public void deleteDeliveryPartner(Long mob) {
	deliveryRepository.deleteBymobno(mob);

}

}
