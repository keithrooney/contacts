package org.contacts.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Objects;

import org.junit.Before;
import org.junit.Test;

public class PersonTest {
	
	private Person person;

	@Before
	public void before() throws Exception {
		person = new Person();
	}
	
	@Test
	public void testGetAndSetFirstname() throws Exception {
		assertNull(person.getFirstname());
		person.setFirstname("firstname");
		assertEquals("firstname", person.getFirstname());
	}
	
	@Test
	public void testGetAndSetSurname() throws Exception {
		assertNull(person.getSurname());
		person.setSurname("surname");
		assertEquals("surname", person.getSurname());
	}
	
	@Test
	public void testHashCode() throws Exception {
		
		Person person = new Person();
		person.setFirstname("firstname");
		person.setSurname("surname");
		
		int expected = Objects.hash("firstname", "surname");
		int actual = person.hashCode();
		
		assertEquals(expected, actual);
		
	}
	
	@Test
	public void testEquals() throws Exception {
		
		Person person0 = new Person();
		person0.setFirstname("firstname");
		person0.setSurname("surname");
		
		assertTrue(person0.equals(person0));
		
		assertFalse(person0.equals(null));
		assertFalse(person0.equals(new Object()));
		
		Person person1 = new Person();
		person1.setFirstname("firstname");
		person1.setSurname("surname");

		assertTrue(person0.equals(person1));
		assertTrue(person1.equals(person0));
		
		Person person2 = new Person();
		person2.setFirstname("firstname");
		person2.setSurname("other");
		
		assertFalse(person0.equals(person2));
		assertFalse(person2.equals(person0));

		
	}
	
}
