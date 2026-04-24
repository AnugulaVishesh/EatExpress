package com.alpha.EatExpress.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.alpha.EatExpress.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}