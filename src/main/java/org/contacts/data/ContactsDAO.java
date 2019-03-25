package org.contacts.data;

import org.contacts.data.filesystem.DistributedFileSystemContactsDAO;
import org.contacts.data.filesystem.LocalFileSystemContactsDAO;
import org.contacts.data.mongodb.MongoContactsDAO;
import org.contacts.model.Contact;

/**
 * A data access object interface for {@link Contact}.
 * 
 * @see DistributedFileSystemContactsDAO
 * @see LocalFileSystemContactsDAO
 * @see MongoContactsDAO
 */
public interface ContactsDAO {

	Contact create(Contact contact);

	Contact update(Contact contact);
	
	Contact get(String email);
	
	boolean delete(String email);
	
}
