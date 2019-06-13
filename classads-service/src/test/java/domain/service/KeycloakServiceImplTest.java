package domain.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.core.HttpHeaders;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import domain.model.User;

@ExtendWith(MockitoExtension.class)
public class KeycloakServiceImplTest {
	
	@InjectMocks
	private KeycloakServiceImpl kcService;
	
	private static final String AUTHORIZATION_PROPERTY = "Authorization";
	private static final String AUTHENTICATION_SCHEME = "Bearer";
	private static String userID = "d9b33355-8e2c-4fc1-9065-8e7086d0696a";
	private static String username = "user1";
	private static String token = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIzNXA2SEJGU3RfUkx3Tj"
			+ "haamZWa3lsTUoydEhEV0xpclUwLTVzcTBFUHBvIn0.eyJqdGkiOiJkZjVkYTgyMi0xNjU4LTQ5NGItOGUwY"
			+ "y0yYjcxMjJmYWEwNDQiLCJleHAiOjE1NjA0MTg5MDQsIm5iZiI6MCwiaWF0IjoxNTYwNDE4NjA0LCJpc3Mi" 
			+ "OiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXV0aC9yZWFsbXMvYXBpZ3ciLCJzdWIiOiJkOWIzMzM1NS04ZTJ"
			+ "jLTRmYzEtOTA2NS04ZTcwODZkMDY5NmEiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJhZG1pbi1jbGkiLCJhdX" 
			+ "RoX3RpbWUiOjAsInNlc3Npb25fc3RhdGUiOiI3MjM2MGJmZC05ZTg5LTQ4NzgtOTk5OC00ODE1OWFiNjJkN"
			+ "jUiLCJhY3IiOiIxIiwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsIm5h"
			+ "bWUiOiJTdMOodmUgSG9zdGV0dGxlciIsInByZWZlcnJlZF91c2VybmFtZSI6InVzZXIxIiwiZ2l2ZW5fbmF"
			+ "tZSI6IlN0w6h2ZSIsImZhbWlseV9uYW1lIjoiSG9zdGV0dGxlciIsImVtYWlsIjoic3RldmUuaG9zdGV0dG"
			+ "xlckBnbWFpbC5jb20ifQ.EegRsElq1xmwX7_etJLFNBkeYaHoGjcOkmz2P2saPVZfSIV29NWbnIMKJjEOzD"
			+ "ZwVjASUQ_khhbA_5swdjbdo6jtEvcPRzqwFY3HgJg30uMzEUvU3W1soR9FPOtUVn9vMl3yoqgehxA6NCyJs"
			+ "8QgPjSNEhNBWUFb1chhIWp1qvQyw4TY6KaxeIGy29Pkx_WeXNnpbT4dBwa8kbm_Q89z60ulT26avwEzv0yW"
			+ "u7vS0NJTf6RVVWiBttkjlGxjU2JXh9O7bVtig8NUdYIOwHG4HSRuozjoevJoCxJBcg73omZ5xFQS-v1GqtF"
			+ "aUsKoP8oTeYhJo4Bg-EeZjQcuwfHVdg";
	
	
	HttpHeaders httpHeaders = mock(HttpHeaders.class);
	ArrayList<String> headers = new ArrayList<String>();
	static Date validDate;
	
	@SuppressWarnings("deprecation")
	@BeforeAll
	public static void setup() {
		DecodedJWT jwt = JWT.decode(token);
		Date issuedAt = jwt.getIssuedAt();
		validDate = (Date) issuedAt.clone();
		validDate.setMinutes(issuedAt.getMinutes()+1);
	}

	@Test
	public void hasValidAuthentificationTest() {
		//First, we suppose there is an authorization header and the time validation is correct
		headers.clear();
		headers.add(AUTHENTICATION_SCHEME + " " + token);
		when(httpHeaders.getRequestHeader(AUTHORIZATION_PROPERTY)).thenReturn(headers);
		Assertions.assertEquals(true, kcService.hasValidAuthentification(httpHeaders, validDate));
		
		//Second, we suppose there isn't an authorization header
		when(httpHeaders.getRequestHeader(AUTHORIZATION_PROPERTY)).thenReturn(new ArrayList<String>());
		Assertions.assertEquals(false, kcService.hasValidAuthentification(httpHeaders, validDate));
		
		//Third, we suppose there is an authorization header but not a correct token
		headers.clear();
		headers.add(AUTHENTICATION_SCHEME + " " + "blablabla");
		when(httpHeaders.getRequestHeader(AUTHORIZATION_PROPERTY)).thenReturn(headers);
		Assertions.assertEquals(false, kcService.hasValidAuthentification(httpHeaders, validDate));

		//Fourth, we suppose everything ok but not the token has expired
		headers.clear();
		headers.add(AUTHENTICATION_SCHEME + " " + token);
		when(httpHeaders.getRequestHeader(AUTHORIZATION_PROPERTY)).thenReturn(headers);
		Assertions.assertEquals(false, kcService.hasValidAuthentification(httpHeaders, new Date()));
	}
	
	@Test
	public void extractUserInfosTest() {
		//We check if the infos are the right
		User user = kcService.extractUserInfos(token);
		Assertions.assertEquals(userID, user.getUserID());
		Assertions.assertEquals(username, user.getUsername());
	}
	

}
