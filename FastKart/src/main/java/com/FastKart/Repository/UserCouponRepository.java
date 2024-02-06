package com.FastKart.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FastKart.entities.UserCoupon;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Integer> {

}
