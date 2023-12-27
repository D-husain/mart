package com.FastKart.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FastKart.entities.ProductDetails;

public interface ProductDetailsRepository extends JpaRepository<ProductDetails, Integer> {

}
