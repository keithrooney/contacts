package org.contacts.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.contacts.model.Contact;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(ContactsController.class)
public class ContactsControllerTest {

	private ObjectMapper objectMapper = new ObjectMapper();
	
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactsService mockContactsService;
	
    @After
    public void after() throws Exception {
    	Mockito.verifyNoMoreInteractions(mockContactsService);
    }
    
	@Test
	public void testCreate() throws Exception {

		Contact contact = new Contact();
		contact.setEmail("keith.rooney@gmail.com");
		
		mockMvc.perform(post("/v1/contacts").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(contact))).andDo(print()).andExpect(status().isOk());
		
		Mockito.verify(mockContactsService).create(contact);
		
	}

	@Test
	public void testUpdate() throws Exception {

		Contact contact = new Contact();
		contact.setEmail("keith.rooney@gmail.com");
		
		mockMvc.perform(put("/v1/contacts").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(contact))).andDo(print()).andExpect(status().isOk());

		Mockito.verify(mockContactsService).update(contact);

	}
	
	@Test
	public void testGet() throws Exception {

		Contact contact = new Contact();
		contact.setEmail("keith.rooney@gmail.com");

		mockMvc.perform(get("/v1/contacts/keith.rooney@gmail.com")).andDo(print()).andExpect(status().isOk());

		Mockito.verify(mockContactsService).get("keith.rooney@gmail.com");
		
	}
	
	@Test
	public void testDelete() throws Exception {

		Contact contact = new Contact();
		contact.setEmail("keith.rooney@gmail.com");

		mockMvc.perform(delete("/v1/contacts/keith.rooney@gmail.com")).andDo(print()).andExpect(status().isOk());

		Mockito.verify(mockContactsService).delete("keith.rooney@gmail.com");
		
	}

	
}
