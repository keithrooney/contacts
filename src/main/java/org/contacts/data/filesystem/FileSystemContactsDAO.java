package org.contacts.data.filesystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.contacts.data.ContactsDAO;
import org.contacts.model.Contact;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * An implementation supplying all the common functionality for a data access
 * object that uses the file system as it's primary means of storage.
 * 
 * @param <T>
 */
public abstract class FileSystemContactsDAO implements ContactsDAO {

	private ObjectMapper objectMapper = new ObjectMapper();
	
	private File storage;
	
	public FileSystemContactsDAO(File storage) {
		this.storage = storage;
	}
	
	@Override
	public Contact create(Contact contact) {
		File file = new File(storage, contact.getEmail());
		if(!file.exists()) {
			try {
				Files.write(file.toPath(), objectMapper.writeValueAsBytes(contact));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			return contact;
		} else {
			throw new IllegalArgumentException("An entry for email " + contact.getEmail() + " already exists.");
		}
	}

	@Override
	public Contact update(Contact contact) {
		File file = new File(storage, contact.getEmail());
		if(file.exists()) {
			try {
				Files.write(file.toPath(), objectMapper.writeValueAsBytes(contact));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			return contact;
		} else {
			throw new IllegalArgumentException("An entry for email " + contact.getEmail() + " does not exist.");
		}
	}
	
	@Override
	public Contact get(String email) {
		File file = new File(storage, email);
		if(file.exists()) {
			try {
				byte[] bytes = Files.readAllBytes(file.toPath());
				return objectMapper.readValue(bytes, Contact.class);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else {
			return null;
		}
	}
	
	@Override
	public boolean delete(String email) {
		Contact contact = get(email);
		if(contact != null) {
			File file = new File(storage, email);
			return file.delete();
		} else {
			return false;
		}
	}
	
}
