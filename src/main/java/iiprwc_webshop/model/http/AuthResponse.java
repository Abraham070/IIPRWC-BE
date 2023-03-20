package iiprwc_webshop.model.http;

public class AuthResponse {
	private String email;
	private String accessToken;

	private String userId;

	private boolean admin;

	public AuthResponse() { }
	
	public AuthResponse(String email, String accessToken, boolean admin, String userId) {
		this.email = email;
		this.accessToken = accessToken;
		this.admin = admin;
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

}
