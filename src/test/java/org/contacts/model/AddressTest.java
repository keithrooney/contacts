package org.contacts.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Objects;

import org.junit.Before;
import org.junit.Test;

public class AddressTest {

	private Address address;

	@Before
	public void before() throws Exception {
		address = new Address();
	}
	
	@Test
	public void testGetAndSetStreet() throws Exception {
		assertNull(address.getStreet());
		address.setStreet("street");
		assertEquals("street", address.getStreet());
	}

	@Test
	public void testGetAndSetLocality() throws Exception {
		assertNull(address.getLocality());
		address.setLocality("locality");
		assertEquals("locality", address.getLocality());
	}

	@Test
	public void testGetAndSetCounty() throws Exception {
		assertNull(address.getCounty());
		address.setCounty("county");
		assertEquals("county", address.getCounty());
	}

	@Test
	public void testGetAndSetCountry() throws Exception {
		assertNull(address.getCountry());
		address.setCountry("country");
		assertEquals("country", address.getCountry());
	}
	
	@Test
	public void testHashCode() throws Exception {
		
		Address address = new Address();
		address.setStreet("street");
		address.setLocality("locality");
		address.setCounty("county");
		address.setCountry("country");
		
		int expected = Objects.hash("street", "locality", "county", "country");
		int actual = address.hashCode();
		
		assertEquals(expected, actual);
		
	}
	
	@Test
	public void testEquals() throws Exception {
		
		Address address0 = new Address();
		address0.setStreet("street");
		address0.setLocality("locality");
		address0.setCounty("county");
		address0.setCountry("country");
		
		assertTrue(address0.equals(address0));
		
		assertFalse(address0.equals(null));
		assertFalse(address0.equals(new Object()));
		
		Address address1 = new Address();
		address1.setStreet("street");
		address1.setLocality("locality");
		address1.setCounty("county");
		address1.setCountry("country");

		assertTrue(address0.equals(address1));
		assertTrue(address1.equals(address0));

		Address address2 = new Address();
		address2.setLocality("locality");
		address2.setCounty("county");
		address2.setCountry("country");
		
		assertFalse(address0.equals(address2));
		assertFalse(address2.equals(address0));
		
	}

}
