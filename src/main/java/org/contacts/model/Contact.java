package org.contacts.model;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.util.StringUtils;

/**
 * A simple pojo to capture information you might find in an address book for a
 * contact.
 */
public class Contact {

	/**
	 * This is overly simplified regular expression to ensure the email is a
	 * valid email.
	 */
	private static final String DEFAULT_EMAIL_REGEX = "[a-zA-Z0-9\\.\\!\"£\\$\\%\\^\\&\\*\\(\\)-_+=]*@[a-zA-Z0-9\\.\\!\"£\\$\\%\\^\\&\\*\\(\\)-_+=]*\\.[a-z]*";
	
	@Id
	private String email;
	
	private Person person = new Person();
	
	private Address address = new Address();
	
	private Mobile mobile = new Mobile();

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if(!StringUtils.isEmpty(email) && email.matches(DEFAULT_EMAIL_REGEX)) {
			this.email = email;
		} else {
			throw new IllegalArgumentException("Expected email to be valid, actual value is [" + email + "].");
		}
	}
	
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Mobile getMobile() {
		return mobile;
	}

	public void setMobile(Mobile mobile) {
		this.mobile = mobile;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(email);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this) {
			return true;
		} else if(obj == null || !(obj instanceof Contact)) {
			return false;
		} else {
			Contact other = (Contact) obj;
			return Objects.equals(email, other.email);
		}
	}

}
