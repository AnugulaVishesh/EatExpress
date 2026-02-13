package com.alpha.EatExpress.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.alpha.EatExpress.Entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
