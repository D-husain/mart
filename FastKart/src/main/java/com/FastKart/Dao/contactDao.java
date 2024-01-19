package com.FastKart.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FastKart.Repository.ContactRepository;
import com.FastKart.entities.Contact;

@Service
public class contactDao {

	@Autowired private ContactRepository contactRepository;

	
	public Contact contact_us(Contact contact) {
		Contact save = contactRepository.save(contact);
		return save;
	}

}
