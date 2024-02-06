package com.FastKart.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.FastKart.entities.Admin;

public interface AdminRepository  extends JpaRepository<Admin, Integer> {


	@Query("select a from Admin a where a.email= :email")
	public Admin findByUsername(@Param("email") String email);

}
