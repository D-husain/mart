package com.FastKart.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FastKart.entities.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

}
