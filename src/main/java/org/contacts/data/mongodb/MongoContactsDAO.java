package org.contacts.data.mongodb;

import java.util.Optional;

import org.contacts.data.ContactsDAO;
import org.contacts.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * An implementation of the {@link ContactsDAO} which interacts
 * with a MongoDB database.
 * 
 * @see MongoDataProperties
 */
public class MongoContactsDAO implements ContactsDAO {

	@Autowired
	private MongoContactsRepository repository;
	
	@Override
	public Contact create(Contact contact) {
		return save(contact);
	}

	@Override
	public Contact update(Contact contact) {
		return save(contact);
	}
	
	private Contact save(Contact contact) {
		repository.save(contact);
		return contact;
	}

	@Override
	public Contact get(String email) {
		Optional<Contact> contact = repository.findById(email);
		if(contact.isPresent()) {
			return contact.get();
		} else {
			return null;
		}
	}

	@Override
	public boolean delete(String email) {
		repository.deleteById(email);
		return true;
	}

}
