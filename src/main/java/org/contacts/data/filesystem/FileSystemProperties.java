package org.contacts.data.filesystem;

import java.io.File;
import java.time.Duration;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix="spring.data.filesystem")
public class FileSystemProperties implements InitializingBean {

	public enum Type {
		
		/**
		 * If this property is set, the application will use
		 * {@link DistributedFileSystemContactsDAO} as the application's primary
		 * choose of storage.
		 */
		distributed,

		/**
		 * If this property is set, the application will use
		 * {@link LocalFileSystemContactsDAO} as the application's primary
		 * choose of storage.
		 */
		local
		
	}
	
	/**
	 * The type of filesystem choose of storage the application will use.
	 * 
	 * @see Type
	 */
	private Type type = Type.local;
	
	/**
	 * The location where all data will be written to.
	 */
	private File storage = new File(System.getProperty("user.home"), ".contacts/storage");
	
	/**
	 * The directory where all locks will be created.<br>
	 * <br>
	 * This is only used with the {@link DistributedFileSystemContactsDAO}.<br>
	 * <br>
	 */
	private File locks = new File(System.getProperty("user.home"), ".contacts/locks");

	/**
	 * This defines how long a lock will exist within the {@link #locks} directory.<br>
	 * <br>
	 * This is only used with the {@link DistributedFileSystemContactsDAO}.<br>
	 * <br>
	 */
	private Duration lease = Duration.ofMinutes(1);
	
	public void setType(Type type) {
		this.type = type;
	}

	public Type getType() {
		return type;
	}
	
	public File getStorage() {
		return storage;
	}

	public void setStorage(File storage) {
		if(storage == null) {
			throw new IllegalArgumentException("Expected storage location not to be null.");
		}
		if(storage.exists() && !storage.isDirectory()) {
			throw new IllegalArgumentException("Expected storage location [" + locks.toString() + "] to be a directory.");
		}
		this.storage = storage;
	}

	public File getLocks() {
		return locks;
	}

	public void setLocks(File locks) {
		if(locks == null) {
			throw new IllegalArgumentException("Expected locks location not to be null.");
		}
		if(locks.exists() && !locks.isDirectory()) {
			throw new IllegalArgumentException("Expected locks location [" + locks.toString() + "] to be a directory.");
		}
		this.locks = locks;
	}

	public Duration getLease() {
		return lease;
	}

	public void setLease(Duration lease) {
		if(lease == null) {
			throw new IllegalArgumentException("Expected lease not to be null.");
		}
		this.lease = lease;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(locks.equals(storage)) {
			throw new IllegalArgumentException("Expected the locks location not to be equal the storage location.");
		}
		if(!storage.exists() && !storage.mkdirs()) {
			throw new IllegalArgumentException("Failed to create storage location [" + storage.toString() + "].");
		}
		if(!locks.exists() && !locks.mkdirs()) {
			throw new IllegalArgumentException("Failed to create locks location [" + locks.toString() + "].");
		}
	}
	
}
