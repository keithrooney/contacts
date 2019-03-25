package org.contacts.controllers;

import org.contacts.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/contacts")
public class ContactsController {

	@Autowired
	private ContactsService service;

	@RequestMapping(method=RequestMethod.POST)
	public void create(@RequestBody Contact contact) {
		service.create(contact);
	}

	@RequestMapping(method=RequestMethod.PUT)
	public void update(@RequestBody Contact contact) {
		service.update(contact);
	}
	
	@RequestMapping("/{id}")
	public Contact get(@PathVariable("id") String email) {
		return service.get(email);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, path="/{id}")
	public boolean delete(@PathVariable("id") String email) {
		return service.delete(email);
	}

}
