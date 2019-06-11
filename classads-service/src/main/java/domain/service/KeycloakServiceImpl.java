package domain.service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
		return getAuthorizationHeader(headers) != null && getToken(headers) != null && extractUserInfos(getToken(headers)) != null;
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
			String authorization = getAuthorizationHeader(headers);

			boolean verif = verifyToken(authorization.replaceFirst(AUTHENTICATION_SCHEME + " ", ""));
			if (!verif) {
				log.info("Verification failed");
			} else {
				log.info("Verification succeded");
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
	public Boolean verifyToken(String token) {
		String url = "http://localhost:8080/auth/realms/apigw/protocol/openid-connect/certs";

		//We send a request to get the key ID
		URL obj;
		try {
			obj = new URL(url);
			HttpURLConnection connexion = (HttpURLConnection) obj.openConnection();
			connexion.setRequestMethod("GET");
			
			//We get the response
			int responseCode = connexion.getResponseCode();
			log.info("Response code : " + responseCode);

			if (responseCode == 200) {

				//We extract the response
				BufferedReader in = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
				String inputLine;
				StringBuilder response = new StringBuilder();
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

				return kidToken.equals(kid);
			}
			return false;
		} catch (MalformedURLException e) {
			log.info("Verification of the token failed :\n" + e.getLocalizedMessage());
			return false;
		} catch (IOException e) {
			log.info("Verification of the token failed :\n" + e.getLocalizedMessage());
			return false;
		}
		

		


	}

}
