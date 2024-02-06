package com.FastKart.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.FastKart.entities.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	@Query("select u from User u where u.email= :email")
	public User getUserByUserName(@Param("email") String email);

	@Query("select u from User u where u.id= :id")
	public User getUserById(@Param("id") Integer userId);

	@Query("SELECT u FROM User u WHERE u.email = ?1")
	public User findByEmail(String email);

	@Query("SELECT u FROM User u WHERE u.email = :email")
	public User getUserByUsername(@Param("email") String username);
	
	@Query("SELECT u FROM User u WHERE u.password = :password")
	public User getUserByPassword(@Param("password") String password);
	
	

}
