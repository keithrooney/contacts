package org.contacts.data.filesystem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.contacts.data.ContactsDAO;
import org.contacts.model.Address;
import org.contacts.model.Contact;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class LocalFileSystemContactsDAOTest {

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	@Test
	public void testJVMFileSystemContactsDAO() throws Exception {

		ContactsDAO dao = new LocalFileSystemContactsDAO(temporaryFolder.newFolder());
		
		Contact contact = new Contact();
		contact.setEmail("keith.rooney@gmail.com");
		
		assertEquals(contact, dao.create(contact));
		try {
			dao.create(contact);
			fail(); // We shouldn't hit here.
		} catch(IllegalArgumentException e) {
			// This is expected, so we'll continue.
		}
		
		assertEquals(contact, dao.get("keith.rooney@gmail.com"));
		
		Address address = new Address();
		address.setCountry("country");
		address.setStreet("street");
		
		contact.setAddress(address);
		
		assertEquals(contact, dao.update(contact));
		
		Contact actual = dao.get("keith.rooney@gmail.com");
		
		assertEquals(address, actual.getAddress());
		
		assertTrue(dao.delete("keith.rooney@gmail.com"));
		assertFalse(dao.delete("keith.rooney@gmail.com"));
		
		assertNull(dao.get("keith.rooney@gmail.com"));
		
		try {
			dao.update(contact);
			fail();
		} catch(IllegalArgumentException e) {
			// This is expected, so we'll continue.
		}
		
	}
	
}
