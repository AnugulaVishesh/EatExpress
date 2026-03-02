package com.alpha.EatExpress.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alpha.EatExpress.entity.Order;
@Repository
public interface OrderRepo extends JpaRepository<Order, Integer>{

}
