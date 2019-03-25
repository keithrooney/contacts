package org.contacts.data.filesystem;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.time.Duration;
import java.util.UUID;

import org.contacts.data.filesystem.FileSystemProperties.Type;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class FileSystemPropertiesTest {

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();
	
	private FileSystemProperties properties;

	@Before
	public void before() throws Exception {
		properties = new FileSystemProperties();
	}
	
	@Test
	public void testGetAndSetType() throws Exception {
		assertEquals(Type.local, properties.getType());
		properties.setType(Type.distributed);
		assertEquals(Type.distributed, properties.getType());
	}

	@Test
	public void testGetAndSetNonExistingStorage() throws Exception {
		assertEquals(new File(System.getProperty("user.home"), ".contacts/storage"), properties.getStorage());
		File file = new File(temporaryFolder.getRoot(), UUID.randomUUID().toString());
		properties.setStorage(file);
		assertEquals(file, properties.getStorage());
	}
	
	@Test
	public void testGetAndSetExistingStorage() throws Exception {
		assertEquals(new File(System.getProperty("user.home"), ".contacts/storage"), properties.getStorage());
		File file = temporaryFolder.newFolder();
		properties.setStorage(file);
		assertEquals(file, properties.getStorage());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetStorageWithNullThrowsException() throws Exception {
		properties.setStorage(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetStorageWithFileThrowsException() throws Exception {
		File file = temporaryFolder.newFile();
		properties.setStorage(file);
	}
	
	@Test
	public void testGetAndSetNonExistingLocks() throws Exception {
		assertEquals(new File(System.getProperty("user.home"), ".contacts/locks"), properties.getLocks());
		File file = new File(temporaryFolder.getRoot(), UUID.randomUUID().toString());
		properties.setLocks(file);
		assertEquals(file, properties.getLocks());
	}

	@Test
	public void testGetAndSetExistingLocks() throws Exception {
		assertEquals(new File(System.getProperty("user.home"), ".contacts/locks"), properties.getLocks());
		File file = temporaryFolder.newFolder();
		properties.setLocks(file);
		assertEquals(file, properties.getLocks());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetLocksWithNullThrowsException() throws Exception {
		properties.setLocks(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetLocksWithFileThrowsException() throws Exception {
		File file = temporaryFolder.newFile();
		properties.setLocks(file);
	}
	
	@Test
	public void testGetAndSetLease() throws Exception {
		assertEquals(Duration.ofMinutes(1), properties.getLease());
		properties.setLease(Duration.ofMinutes(4));
		assertEquals(Duration.ofMinutes(4), properties.getLease());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetInvalidLeaseThrowsException() throws Exception {
		properties.setLease(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAfterPropertiesSetThrowsException() throws Exception {
		
		properties.setLocks(temporaryFolder.newFolder());
		properties.setStorage(temporaryFolder.newFolder());
		
		properties.afterPropertiesSet();

		File file = temporaryFolder.newFolder();
		
		properties.setLocks(file);
		properties.setStorage(file);
		
		properties.afterPropertiesSet();
		
	}

}
