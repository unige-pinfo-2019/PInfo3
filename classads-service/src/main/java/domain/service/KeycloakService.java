package domain.service;

import javax.ws.rs.core.HttpHeaders;

import domain.model.User;

public interface KeycloakService {
	
	
	public boolean hasValidAuthentification(HttpHeaders headers);
	public String getAuthorizationHeader(HttpHeaders headers);
	public String getToken(HttpHeaders headers);
	public User extractUserInfos(String token);
	public Boolean verifyToken(String token);
	public Boolean verifyExpirationTime(String token);

}
