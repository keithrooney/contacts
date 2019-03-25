package org.contacts.model;

import java.util.Objects;

/**
 * A simple pojo to capture information you might find in an address book for a
 * contact related to the person.
 */
public class Person {

	private String firstname;
	
	private String surname;

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(firstname, surname);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		} else if(obj == null || !(obj instanceof Person)) {
			return false; 
		} else {
			Person other = (Person) obj;
			return Objects.equals(firstname, other.firstname) &&
					Objects.equals(surname, other.surname);
		}
	}
	
}
