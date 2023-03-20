package iiprwc_webshop.model.http;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class AuthRequest {

	/**
	 * The email address of the user.
	 * This is the username.
	 */
	@NotNull @Email @Length(min = 5, max = 50)
	private String email;

	/**
	 * The password of the user.
	 */
	@NotNull @Length(min = 5, max = 124)
	private String password;

	public AuthRequest() {
		
	}
	
	public AuthRequest(String email,String password) {
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
