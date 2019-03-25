package org.contacts.data.filesystem;

import java.io.File;

import org.contacts.concurrent.locks.FileLock;
import org.contacts.concurrent.locks.FileLockService;
import org.contacts.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Given it was detailed in the product requirements that we'd have access to
 * the local file system, the {@link DistributedFileSystemContactsDAO} intention
 * is to allow sharing of the same storage location amongst multiple instances
 * of this application, which in turn will allow us to scale horizontally.<br>
 * <br>
 * An alternative to using the local file system is to use ZooKeeper, locking on
 * the email for a {@link Contact}, which would be more a production ready
 * solution. However, given the previously mentionned requirements, this is the
 * next best solution.<br>
 * <br>
 */
public class DistributedFileSystemContactsDAO extends FileSystemContactsDAO {

	private FileLockService fileLockService;
	
	public DistributedFileSystemContactsDAO(File storage) {
		super(storage);
	}

	@Override
	public Contact create(Contact contact) {
		FileLock lock = fileLockService.create(contact.getEmail());
		try {
			lock.lock();
			return super.create(contact);
		} finally {
			lock.unlock();
		}
	}
	
	@Override
	public Contact update(Contact contact) {
		FileLock lock = fileLockService.create(contact.getEmail());
		try {
			lock.lock();
			return super.update(contact);
		} finally {
			lock.unlock();
		}
	}
	
	@Override
	public boolean delete(String email) {
		FileLock lock = fileLockService.create(email);
		try {
			lock.lock();
			return super.delete(email);
		} finally {
			lock.unlock();
		}
	}

	public FileLockService getFileLockService() {
		return fileLockService;
	}

	@Autowired
	public void setFileLockService(FileLockService fileLockService) {
		this.fileLockService = fileLockService;
	}
	
}
