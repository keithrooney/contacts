package org.contacts.concurrent.locks;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class FileLockServiceTest {

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	@Test
	public void testFileLockService() throws Exception {
		
		File locks = temporaryFolder.newFolder();
		
		FileLockService service = new FileLockService(locks, Duration.ofSeconds(3));
		
		FileLock lock0 = service.create("lock0");
		
		lock0.lock();

		File file0 = new File(locks, "lock0");
		assertTrue(file0.exists());

		FileLock lock1 = service.create("lock1");

		lock1.lock();
				
		File file1 = new File(locks, "lock1");
		assertTrue(file1.exists());

		file1.setLastModified(System.currentTimeMillis() + Duration.ofMinutes(5).toMillis());
		
		service.afterPropertiesSet();

		Thread.sleep(TimeUnit.SECONDS.toMillis(2));
		
		assertFalse(file0.exists());
		assertTrue(file1.exists());
		
	}
	
}
