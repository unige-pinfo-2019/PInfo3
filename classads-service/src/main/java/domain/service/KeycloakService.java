package domain.service;

import java.util.Date;

import javax.ws.rs.core.HttpHeaders;

import domain.model.User;

public interface KeycloakService {
	
	
	public boolean hasValidAuthentification(HttpHeaders headers, Date now);
	public String getAuthorizationHeader(HttpHeaders headers);
	public String getToken(HttpHeaders headers);
	public User extractUserInfos(String token);
	public Boolean verifyExpirationTime(String token, Date now);

}
