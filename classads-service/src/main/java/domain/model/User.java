package domain.model;

import lombok.Data;

@Data
public class User {

	private String userID;
	private String username;

	public User(String userID, String username) {
		this.userID = userID;
		this.username = username;
	}

}
