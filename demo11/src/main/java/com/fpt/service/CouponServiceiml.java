package com.fpt.service;

import com.fpt.repository.CouponRepository;
import com.fpt.model.Coupon;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CouponServiceiml implements CouponService {
    public final CouponRepository couponRepository;

    public CouponServiceiml(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public Coupon createCoupon(Coupon coupon) {
        Coupon cou = new Coupon();
        cou.setName(coupon.getName());
        cou.setCode(coupon.getCode());
        cou.setDiscount(coupon.getDiscount());
        couponRepository.save(cou);
        return cou;
    }

    @Override
    public Coupon findById(Long id) {
        Optional<Coupon> couponOptional = couponRepository.findById(id);
        if (couponOptional.isPresent()) {
            Coupon coupon = couponOptional.get();
            return coupon;
        }
        return null;
    }

    @Override
    public Coupon findByName(String name) {
        Coupon coupon = couponRepository.findCouponByName(name);
        if (coupon != null) {
            return coupon;
        }
        return null;
    }

    @Override
    public List<Coupon> searchCouponByName(String name) {
        try {
            List<Coupon> list = couponRepository.findByNameContainingIgnoreCase(name);
            return list;
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public List<Coupon> listAllCoupon() {
        return couponRepository.findAll();
    }

    @Override
    public boolean deleteCoupon(Long id) {
        Optional<Coupon> couponOptional = couponRepository.findById(id);
        if (couponOptional.isPresent()) {
            Coupon coupon = couponOptional.get();
            couponRepository.delete(coupon);
            return true;
        }
        return false;
    }
    @Override
    public Coupon updateCoupon(Long id, Coupon coupon) {
        Optional<Coupon> couponOptional = couponRepository.findById(id);
        if (couponOptional.isPresent()) {
            Coupon co = couponOptional.get();
            co.setName(coupon.getName());
            co.setCode(coupon.getCode());
            co.setDiscount(coupon.getDiscount());
            couponRepository.save(co);
            return co;
        }
        return null;
    }
}
