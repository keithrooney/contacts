package org.contacts.controllers;

import org.contacts.data.ContactsDAO;
import org.contacts.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContactsService {

	@Autowired
	private ContactsDAO contactDAO;
	
	public void create(Contact contact) {
		contactDAO.create(contact);
	}

	public void update(Contact contact) {
		contactDAO.update(contact);
	}
	
	public Contact get(String email) {
		return contactDAO.get(email);
	}
	
	public boolean delete(String email) {
		return contactDAO.delete(email);
	}

}
