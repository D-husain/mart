package com.FastKart.Dao;

import java.util.List;
import java.util.Optional;

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

	public List<Contact> ShowContact(){
		return contactRepository.findAll();
	}
	

	public Contact getContactByIds(Integer id) {
		return contactRepository.findById(id).get();
	}
	
	public void ReplayMessage(int contactId, String email, String replay) {
		Optional<Contact> contact = contactRepository.findById(contactId);
        contact.ifPresent(contacts -> {
            contacts.setEmail(email);
            contacts.setReplay(replay);
            contactRepository.save(contacts);
        });
	}
}
