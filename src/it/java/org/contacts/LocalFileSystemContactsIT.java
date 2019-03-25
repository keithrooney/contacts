package org.contacts;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.contacts.model.Address;
import org.contacts.model.Contact;
import org.hamcrest.Matchers;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@ContextConfiguration
@AutoConfigureMockMvc
@SpringBootTest(
	classes = { Main.class }, 
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class LocalFileSystemContactsIT {
	
	@ClassRule
	public static TemporaryFolder temporaryFolder = new TemporaryFolder();
	
	@Autowired
	private ObjectMapper objectMapper;
	
    @Autowired
    private MockMvc mockMvc;
	
	@Test
	public void testApplication() throws Exception {
		
		Contact contact = new Contact();
		contact.setEmail(UUID.randomUUID().toString() + "@" + UUID.randomUUID().toString() + ".ie");
		
		mockMvc.perform(post("/v1/contacts").content(objectMapper.writeValueAsBytes(contact))
				.contentType(MediaType.APPLICATION_JSON_UTF8)).andDo(print())
				.andExpect(status().isOk());
		
		mockMvc.perform(get("/v1/contacts/" + contact.getEmail())).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.email", Matchers.is(contact.getEmail())));

		Address address = new Address();
		address.setCountry("country");
		contact.setAddress(address);
		
		mockMvc.perform(put("/v1/contacts").content(objectMapper.writeValueAsBytes(contact))
				.contentType(MediaType.APPLICATION_JSON_UTF8)).andDo(print())
				.andExpect(status().isOk());
		
		mockMvc.perform(get("/v1/contacts/" + contact.getEmail())).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.address.country", Matchers.is(address.getCountry())));

		mockMvc.perform(delete("/v1/contacts/" + contact.getEmail())).andDo(print()).andExpect(status().isOk());

	}

}
