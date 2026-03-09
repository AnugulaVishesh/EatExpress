package com.alpha.EatExpress.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.EatExpress.Servicee.RedisService;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @PostMapping("/updatelocation")
    public String updateLocation(@RequestParam Integer partnerid,
                                 @RequestParam double latitude,
                                 @RequestParam double longitude) {

        return redisService.updateDpLoc(partnerid, latitude, longitude);
    }

    @GetMapping("/findnearbypartners")
    public List<String> findNearbyPartners(@RequestParam double latitude,
                                           @RequestParam double longitude,
                                           @RequestParam double radius) {

        return redisService.findNearbyPartners(latitude, longitude, radius);
    }
}