package com.alpha.EatExpress.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.alpha.EatExpress.Entity.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
