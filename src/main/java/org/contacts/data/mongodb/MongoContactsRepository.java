package org.contacts.data.mongodb;

import org.contacts.model.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoContactsRepository extends MongoRepository<Contact, String> {

}
