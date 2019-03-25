package org.contacts.controllers;

import org.contacts.data.ContactsDAO;
import org.contacts.model.Contact;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ContactsServiceTest {

	@InjectMocks
	private ContactsService contactsService;
	
	@Mock
	private ContactsDAO mockContactsDAO;
	
	@After
	public void after() throws Exception {
		Mockito.verifyNoMoreInteractions(mockContactsDAO);
	}
	
	@Test
	public void testCreate() throws Exception {
		contactsService.create(new Contact());
		Mockito.verify(mockContactsDAO).create(new Contact());
	}

	@Test
	public void testUpdate() throws Exception {
		contactsService.update(new Contact());
		Mockito.verify(mockContactsDAO).update(new Contact());
	}

	@Test
	public void testGet() throws Exception {
		contactsService.get("email");
		Mockito.verify(mockContactsDAO).get("email");
	}

	@Test
	public void testDelete() throws Exception {
		contactsService.delete("email");
		Mockito.verify(mockContactsDAO).delete("email");
	}

}
