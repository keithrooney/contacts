package org.contacts.model;

import java.util.Objects;

/**
 * A simple pojo to capture information you might find in an address book for a
 * contact related to an address.
 */
public class Address {

	private String street;
	
	private String locality;
	
	private String county;
	
	private String country;

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(street, locality, county, country);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		} else if(obj == null || !(obj instanceof Address)) {
			return false; 
		} else {
			Address other = (Address) obj;
			return Objects.equals(street, other.street) &&
					Objects.equals(locality, other.locality) &&
					Objects.equals(county, other.county) &&
					Objects.equals(country, other.country);
		}
	}
	
}
