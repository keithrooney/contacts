package org.contacts.data.mongodb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.contacts.model.Contact;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MongoContactsDAOTest {

	@InjectMocks
	private MongoContactsDAO dao;
	
	@Mock
	private MongoContactsRepository mockRespository;
	
	@Test
	public void testCreate() throws Exception {
		
		Contact contact = new Contact();
		contact.setEmail("keith.rooney@gmail.com");
		
		Mockito.when(mockRespository.save(contact)).thenReturn(contact);
		
		dao.create(contact);
	
		Mockito.verify(mockRespository).save(contact);

	}

	@Test
	public void testUpdate() throws Exception {
		
		Contact contact = new Contact();
		contact.setEmail("keith.rooney@gmail.com");
		
		Mockito.when(mockRespository.save(contact)).thenReturn(contact);
		
		dao.update(contact);
	
		Mockito.verify(mockRespository).save(contact);
		
	}

	@Test
	public void testGet() throws Exception {

		Contact contact = new Contact();
		contact.setEmail("keith.rooney@gmail.com");

		Mockito.when(mockRespository.findById("keith.rooney@gmail.com")).thenReturn(Optional.of(contact));
		
		assertEquals(contact, dao.get("keith.rooney@gmail.com"));

		Mockito.when(mockRespository.findById("keith.rooney@gmail.com")).thenReturn(Optional.empty());

		assertNull(dao.get("keith.rooney@gmail.com"));
		
		Mockito.verify(mockRespository, Mockito.times(2)).findById("keith.rooney@gmail.com");
		
	}

	@Test
	public void testDelete() throws Exception {
		
		assertTrue(dao.delete("keith.rooney@gmail.com"));
		
		Mockito.verify(mockRespository).deleteById("keith.rooney@gmail.com");
		
	}

}
