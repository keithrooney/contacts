package org.contacts.data.filesystem;

import java.io.File;

import org.contacts.model.Contact;

/**
 * This is extension of the {@link JVMFileSystemContactsDAO} primary goal is to
 * ensure thread safety within a single JVM.<br>
 * <br>
 * We try to mimick the behaviour of an actual database acquire locks when
 * carrying out an update, create or delete to ensure thread safety.<br>
 * <br>
 * In the case of the insert, we make use of {@link #sentinal} with synchronized
 * to mirror that of a table lock.<br>
 * <br>
 * Regards the update and delete operations, we use the
 * {@link Contact#getEmail()} for synchronization which mimicks that of a row
 * exlusive lock.<br>
 * <br>
 * 
 * @see DistributedFileSystemDAO
 */
public class LocalFileSystemContactsDAO extends FileSystemContactsDAO {

	private Object sentinal = new Object();
	
	public LocalFileSystemContactsDAO(File storage) {
		super(storage);
	}
	
	@Override
	public Contact create(Contact contact) {
		synchronized (sentinal) {
			return super.create(contact);
		}
	}

	@Override
	public Contact update(Contact contact) {
		synchronized (contact.getEmail()) {
			return super.update(contact);
		}
	}
	
	@Override
	public boolean delete(String email) {
		synchronized (email) {
			return super.delete(email);
		}
	}
	
}
