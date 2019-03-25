package org.contacts.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Objects;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class ContactTest {

	private Contact contact;

	@Before
	public void before() throws Exception {
		contact = new Contact();
	}
	
	@Test
	public void testGetAndSetEmail() throws Exception {
		assertNull(contact.getEmail());
		contact.setEmail("keith.rooney@gmail.com");
		assertEquals("keith.rooney@gmail.com", contact.getEmail());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetInvalidEmailThrowsException() throws Exception {
		contact.setEmail("email");
	}

	@Test
	public void testGetAndSetPerson() throws Exception {

		assertEquals(new Person(), contact.getPerson());
		
		Person person = new Person();
		person.setFirstname("firstname");
		
		contact.setPerson(person);
		
		assertEquals(person, contact.getPerson());
		
	}

	@Test
	public void testGetAndSetAddress() throws Exception {
		
		assertEquals(new Address(), contact.getAddress());
		
		Address address = new Address();
		address.setCountry("country");
		
		contact.setAddress(address);
		
		assertEquals(address, contact.getAddress());
		
	}

	@Test
	public void testGetAndSetMobile() throws Exception {
		
		assertEquals(new Mobile(), contact.getMobile());
		
		Mobile mobile = new Mobile();
		mobile.setCode("353");
		
		contact.setMobile(mobile);
		
		assertEquals(mobile, contact.getMobile());
		
	}

	@Test
	public void testHashCode() throws Exception {
		
		Contact contact = new Contact();
		contact.setEmail("wolverine@xmen.org");
		
		int expected = Objects.hash("wolverine@xmen.org");
		int actual = contact.hashCode();
		
		assertEquals(expected, actual);
		
		
	}
	
	@Test
	public void testEquals() throws Exception {
		
		String email = UUID.randomUUID().toString() + "@" + UUID.randomUUID().toString() + ".ie";
		
		Contact contact0 = new Contact();
		contact0.setEmail(email);
		
		assertTrue(contact0.equals(contact0));
		
		assertFalse(contact0.equals(null));
		assertFalse(contact0.equals(new Object()));
		
		Contact contact1 = new Contact();
		contact1.setEmail(email);
		
		assertTrue(contact0.equals(contact1));
		assertTrue(contact1.equals(contact0));
		
		Contact contact2 = new Contact();
		contact2.setEmail("another@jobvite5343.ie");

		assertFalse(contact0.equals(contact2));
		assertFalse(contact2.equals(contact0));

	}

}
