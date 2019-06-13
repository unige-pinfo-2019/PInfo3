package domain.model;

import lombok.Data;

@Data
public class User {

	private String userID;
	private String username;
	private String userEmail;

	public User(String userID, String username, String userEmail) {
		this.userID = userID;
		this.username = username;
		this.userEmail = userEmail;
	}

}
