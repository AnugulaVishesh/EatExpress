package com.alpha.EatExpress.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.alpha.EatExpress.Entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
