package com.alpha.EatExpress.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.alpha.EatExpress.Entity.Item;
@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
}
