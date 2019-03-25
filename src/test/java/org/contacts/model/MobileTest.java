package org.contacts.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Objects;

import org.junit.Before;
import org.junit.Test;

public class MobileTest {

	private Mobile mobile;

	@Before
	public void before() throws Exception {
		mobile = new Mobile();
	}
	
	@Test
	public void testGetAndSetCode() throws Exception {
		assertNull(mobile.getCode());
		mobile.setCode("353");
		assertEquals("353", mobile.getCode());
		mobile.setCode(null);
		assertNull(mobile.getCode());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetInvalidCodeThrowsException() throws Exception {
		mobile.setCode("32");
	}

	@Test
	public void testGetAndSetNetwork() throws Exception {
		assertNull(mobile.getNetwork());
		mobile.setNetwork("087");
		assertEquals("087", mobile.getNetwork());
		mobile.setNetwork(null);
		assertNull(mobile.getNetwork());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSetInvalidNetworkThrowsException() throws Exception {
		mobile.setNetwork("9");
	}
	
	@Test
	public void testGetAndSetNumber() throws Exception {
		assertNull(mobile.getNumber());
		mobile.setNumber("07591234");
		assertEquals("07591234", mobile.getNumber());
		mobile.setNumber(null);
		assertNull(mobile.getNumber());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetInvalidNumberThrowsException() throws Exception {
		mobile.setNumber("342323");
	}

	@Test
	public void testHashCode() throws Exception {
		
		Mobile mobile = new Mobile();
		mobile.setCode("353");
		mobile.setNetwork("087");
		mobile.setNumber("07591234");
		
		int expected = Objects.hash("353", "087", "07591234");
		int actual = mobile.hashCode();
		
		assertEquals(expected, actual);
		
	}
	
	@Test
	public void testEquals() throws Exception {
		
		Mobile mobile0 = new Mobile();
		mobile0.setCode("353");
		mobile0.setNetwork("087");
		mobile0.setNumber("07591234");

		assertTrue(mobile0.equals(mobile0));
		
		assertFalse(mobile0.equals(null));
		assertFalse(mobile0.equals(new Object()));
		
		Mobile mobile1 = new Mobile();
		mobile1.setCode("353");
		mobile1.setNetwork("087");
		mobile1.setNumber("07591234");

		assertTrue(mobile0.equals(mobile1));
		assertTrue(mobile1.equals(mobile0));
		
		Mobile mobile2 = new Mobile();
		mobile2.setCode("345");
		mobile2.setNetwork("535");
		mobile2.setNumber("07591234");

		assertFalse(mobile0.equals(mobile2));
		assertFalse(mobile2.equals(mobile0));
		
	}
	
	@Test
	public void testAfterPropertiesSet() throws Exception {
		
		Mobile mobile0 = new Mobile();

		try {
			mobile0.afterPropertiesSet();
		} catch(IllegalArgumentException e) {
			// Reaching here is unexpected.
			fail();
		}
		
		Mobile mobile1 = new Mobile();
		mobile1.setCode("353");
		
		try {
			mobile1.afterPropertiesSet();
			fail();
		} catch(IllegalArgumentException e) {
			// This is expected, so we'll continue.
		}

		Mobile mobile2 = new Mobile();
		mobile2.setCode("353");
		mobile2.setNetwork("353");
		
		try {
			mobile2.afterPropertiesSet();
			fail();
		} catch(IllegalArgumentException e) {
			// This is expected, so we'll continue.
		}

		Mobile mobile3 = new Mobile();
		mobile3.setCode("353");
		mobile3.setNumber("876544321");
		
		try {
			mobile3.afterPropertiesSet();
			fail();
		} catch(IllegalArgumentException e) {
			// This is expected, so we'll continue.
		}

		Mobile mobile4 = new Mobile();
		mobile4.setNetwork("353");
		mobile4.setNumber("876544321");
		
		try {
			mobile4.afterPropertiesSet();
			fail();
		} catch(IllegalArgumentException e) {
			// This is expected, so we'll continue.
		}
		
		Mobile mobile5 = new Mobile();
		mobile5.setCode("456");
		mobile5.setNetwork("353");
		mobile5.setNumber("876544321");
		
		try {
			mobile5.afterPropertiesSet();
		} catch(IllegalArgumentException e) {
			fail();
		}

	}
	
}
