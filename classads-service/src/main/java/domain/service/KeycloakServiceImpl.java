package domain.service;


import java.util.Date;

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
		if (getAuthorizationHeader(headers) == null)
			return false;

		String token = getToken(headers);
		if (token != null) {
			return extractUserInfos(token) != null && verifyExpirationTime(token);
		}
		return false;
    }

	@Override
	public String getAuthorizationHeader(HttpHeaders headers) {
		try {
			return headers.getRequestHeader(AUTHORIZATION_PROPERTY).get(0);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	@Override
	public String getToken(HttpHeaders headers) {
		try {
			return getAuthorizationHeader(headers).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
		} catch (Exception e) {
			return null;
		}

	}

	@Override
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

	@Override
	public Boolean verifyExpirationTime(String token) {
		DecodedJWT jwt = JWT.decode(token);

		Date issuedAt = jwt.getIssuedAt();
		Date notBefore = jwt.getNotBefore();
		Date expiresAt = jwt.getExpiresAt();

		Date now = new Date();
		return now.after(notBefore) && now.after(issuedAt) && now.before(expiresAt);
	}

}
