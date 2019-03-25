package org.contacts.concurrent.locks;

import java.io.File;
import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.InitializingBean;


public class FileLockService implements InitializingBean {

	private static final Duration DEFAULT_LEASE = Duration.ofMinutes(1);

	private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	
	private File locks;

	private Duration lease = DEFAULT_LEASE;
	
	public FileLockService(File locks, Duration lease) {
		this.locks = locks;
		this.lease  = lease;
	}
	
	public FileLock create(String name) {
		return new FileLock(new File(locks, name));
	}

	public void release() {
		long timestamp = System.currentTimeMillis();
		long deadline = timestamp + lease.toMillis();
		File[] files = locks.listFiles();
		for(File file: files) {
			if((deadline > file.lastModified()) && !file.delete()) {
				throw new IllegalStateException("Failed to delete lock file " + file.toString() + ".");
			}
		}
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		executor.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				release();
			}
			
		}, 0, lease.toNanos(), TimeUnit.NANOSECONDS);
	}
	
}
