package org.contacts.model;

import java.util.Objects;

import org.springframework.beans.factory.InitializingBean;

/**
 * A simple pojo to capture information you might find in an address book for a
 * contact related to an mobile.
 */
public class Mobile implements InitializingBean {
	
	private static final String DEFAULT_CODE_REGEX = "[0-9]{3,}";

	private static final String DEFAULT_NETWORK_REGEX = "[0-9]{3,}";

	private static final String DEFAULT_DIGITS_REGEX = "[0-9]{8,}";

	private String code;
	
	private String network;
	
	private String number;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		if(code == null || (code != null && code.matches(DEFAULT_CODE_REGEX))) {
			this.code = code;
		} else {
			throw new IllegalArgumentException("Expected country code to be at least 3 digits, actual value is [" + network + "].");
		}
	}

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		if(network == null || (network != null && network.matches(DEFAULT_NETWORK_REGEX))) {
			this.network = network;
		} else {
			throw new IllegalArgumentException("Expected network to contain at least 3 or more digits, actual value is [" + network + "].");
		}
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		if(number == null || (number != null && number.matches(DEFAULT_DIGITS_REGEX))) {
			this.number = number;
		} else {
			throw new IllegalArgumentException("Expected number to be at least 8 digits long, actual value is [" + number + "].");
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(code, network, number);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		} else if(obj == null || !(obj instanceof Mobile)) {
			return false; 
		} else {
			Mobile other = (Mobile) obj;
			return Objects.equals(code, other.code) &&
					Objects.equals(network, other.network) &&
					Objects.equals(number, other.number);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if((code != null || network != null || number != null) &&
			(code == null || network == null || number == null)) {
			throw new IllegalArgumentException("Expected code, network and number to be present.");
		}
	}
	
}
