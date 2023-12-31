package com.fpt.repository;
import com.fpt.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    List<Coupon> findByNameContainingIgnoreCase(String name);
    Coupon findCouponByName(String name);
}
