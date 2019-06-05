package domain.service;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.HttpHeaders;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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

			try {
				log.info("Verification token : " + verifyToken(authorization.replaceFirst(AUTHENTICATION_SCHEME + " ", "")));
			} catch (Exception e) {
				log.info("Verification failed");
			}
			return authorization.replaceFirst(AUTHENTICATION_SCHEME + " ", "");
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
	public Boolean verifyToken(String token) throws Exception {
		String url = "http://localhost:8080/auth/realms/apigw/protocol/openid-connect/certs";
		
		//We send a request to get the key ID
		URL obj = new URL(url);
		HttpURLConnection connexion = (HttpURLConnection) obj.openConnection();
		connexion.setRequestMethod("GET");

		//We get the response
		int responseCode = connexion.getResponseCode();
		log.info("Response code : " + responseCode);
		
		if (responseCode == 200) {
			
			//We extract the response
			BufferedReader in = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			//We convert the string in json
			JsonArray jsonArray = new JsonParser().parse(response.toString()).getAsJsonArray();
			
			//We extract the keyID
			JsonObject json = jsonArray.get(0).getAsJsonObject();
			String kid = json.get("kid").getAsString();
			
			//We compare with the keyID in the token
			DecodedJWT jwt = JWT.decode(token);
			Claim claim = jwt.getHeaderClaim("kid");
			String kidToken = claim.asString();
			
			if (kidToken.equals(kid)) {
				return true;
			}
			return false;
		}
		return false;
 
	   
	}
	
}
