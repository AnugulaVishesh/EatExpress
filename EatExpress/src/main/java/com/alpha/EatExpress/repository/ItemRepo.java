package com.alpha.EatExpress.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alpha.EatExpress.entity.Item;
@Repository
public interface ItemRepo extends JpaRepository<Item, Integer> {

}
