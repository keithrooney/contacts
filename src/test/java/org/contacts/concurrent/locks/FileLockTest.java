package org.contacts.concurrent.locks;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class FileLockTest {
	
	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();
	
	@Test
	public void testFileLock() throws Exception {
		
		File file = new File(temporaryFolder.getRoot(), UUID.randomUUID().toString());

		assertFalse(file.exists());
		
		FileLock lock = new FileLock(file);
		
		lock.lock();

		assertTrue(file.exists());
		
		assertFalse(lock.tryLock(5, TimeUnit.SECONDS));
		
		lock.unlock();
		
		assertFalse(file.exists());

		assertTrue(lock.tryLock(0, TimeUnit.SECONDS));
		
	}
	
//	private static class Worker extends Thread {
//		
//		private CountDownLatch latch;
//		
//		private FileLock lock;
//
//		public Worker(CountDownLatch latch, FileLock lock) {
//			this.latch = latch;
//			this.lock = lock;
//		}
//		
//		@Override
//		public void run() {
//			latch.countDown();
//			try {
//				latch.await();
//			} catch (InterruptedException e) {
//				fail();
//			}
//			try {
//				lock.lock();
//				System.out.println("Hello, World!");
//			} finally {
//				lock.unlock();
//			}
//		}
//		
//	}

}
