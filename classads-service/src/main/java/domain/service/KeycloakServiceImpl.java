package domain.service;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.HttpHeaders;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import domain.model.User;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class KeycloakServiceImpl implements KeycloakService {
	
	private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Bearer";    

	@Override
	public boolean hasValidAuthentification(HttpHeaders headers) {
		return getAuthorizationHeader(headers) != null && getToken(headers) != null;
	}
	
	@Override
	public String getAuthorizationHeader(HttpHeaders headers) {
		try {
			String authorization = headers.getRequestHeader(AUTHORIZATION_PROPERTY).get(0);
			return authorization;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	@Override
	public String getToken(HttpHeaders headers) {
		try {
			String authorization = getAuthorizationHeader(headers);
			return authorization.replaceFirst(AUTHENTICATION_SCHEME + " ", "");
		} catch (Exception e) {
			return null;
		}
		
	}
	
	public User extractUserInfos(String token) {
		try {
		    DecodedJWT jwt = JWT.decode(token);
		    Claim claim = jwt.getClaim("preferred_username");
		    return new User(jwt.getSubject(), claim.asString());
		    
		} catch (JWTDecodeException exception){
		    //Invalid token
			log.info("Decoding token didn't work");
			return null;
		}
	}
}
