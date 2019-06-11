package domain.service;

import java.net.http.HttpClient;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;

import org.apache.http.Header;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import domain.model.User;

import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;

import io.undertow.util.Headers;

@ExtendWith(MockitoExtension.class)
public class KeycloakServiceImplTest {
	
	@Mock
	private KeycloakServiceImpl keycloakService;
	
	private static final String AUTHENTICATION_SCHEME = "Bearer";
	private String userID = "53862583-b2c7-424c-a702-731a40372277";
	private String username = "user1";
	private String kid = "lin9f-L-ZpcA2l9TcsYATbuRuyEPyspCZZV0YnDUzWA";
	private String token =  "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJsaW45Zi1MLVpwY0EybDlUY3NZQVRidVJ1eUVQeXNwQ1paVjBZb"
			+ "kRVeldBIn0.eyJqdGkiOiI1ZjY0YzJmNy1kZmI5LTRiNzEtYTBiNy0xYTg5NzgxZDAwOWIiLCJleHAiOjE1NjAyNDgyNDUsIm5iZiI6MCwiaWF0Ijox" 
			+ "NTYwMjQ3OTQ1LCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXV0aC9yZWFsbXMvYXBpZ3ciLCJzdWIiOiI1Mzg2MjU4My1iMmM3LTQyNGMtYTc"
			+ "wMi03MzFhNDAzNzIyNzciLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJhZG1pbi1jbGkiLCJhdXRoX3RpbWUiOjAsInNlc3Npb25fc3RhdGUiOiI0Mzk2OT"
			+ "U1MS01ZDIzLTQ1NDUtOTVmNC02NjM0M2JlZmI0YzciLCJhY3IiOiIxIiwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwiZW1haWxfdmVyaWZpZWQiOnRyd" 
			+ "WUsIm5hbWUiOiJTdMOodmUgSG9zdGV0dGxlciIsInByZWZlcnJlZF91c2VybmFtZSI6InVzZXIxIiwiZ2l2ZW5fbmFtZSI6IlN0w6h2ZSIsImZhbWls" 
			+ "eV9uYW1lIjoiSG9zdGV0dGxlciIsImVtYWlsIjoic3RldmUuaG9zdGV0dGxlckBnbWFpbC5jb20ifQ.F_5CBZp2qf2Sgdq-s48-GYhuyPT9wuSPsuUs" 
			+ "olCP3keZ44T1L0sJ-QWgh_fjhn5p2oseC6CqE8_L2BCOJDrF1R9_q1not4Iz3Yp5LqS6W5VXD70b5oWX0-ePjiQscvvm3OjdJ-hMZDLrnl1-1hJ6kmZ" 
			+ "g4aBX3hbkYMuCxEDk-0wTizd1bqf56ZVlEfWhsbwjVVS91_WXezaIinmDWVVcN8ZqKlrnpoYHq6_5vFHWMhJmD2Q6viTkzqvKsKlhXUAO4vumC7K7DD" 
			+ "snufsFf4mhWuCJMD2NYSEZunI57M6GgE-gRBiTAmyXr-FZ7BH-XLJ3USiPeyQjpmaBFfS-YHz_GQ";
	
	@Test
	public void hasValidAuthentificationTest() {
		
		when(keycloakService.getAuthorizationHeader(any())).thenReturn(AUTHENTICATION_SCHEME + " " + token);
		when(keycloakService.getToken(any(HttpHeaders.class))).thenReturn(token);
//		
//		keycloakService.hasValidAuthentification(any(HttpHeaders.class));
//		verify(keycloakService).verifyExpirationTime(token);
//		verify(keycloakService).extractUserInfos(token);
		
//		when(keycloakService.getAuthorizationHeader(any(HttpHeaders.class))).thenReturn(AUTHENTICATION_SCHEME + " " + token);
//		when(keycloakService.getToken(any(HttpHeaders.class))).thenReturn(token);
//		
//		if (getAuthorizationHeader(headers) == null)
//			return false;
//
//		String token = getToken(headers);
//		if (token != null) {
//			return verifyExpirationTime(token) && extractUserInfos(token) != null;
//		}
//		return false;
	}
	
	@Test
	public void extractUserInfosTest() {
		User user = keycloakService.extractUserInfos(token);
		
		Assertions.assertEquals(username, user.getUsername());
		Assertions.assertEquals(userID, user.getUserID());
	}
	
	@Test 
	public void verifyTokenTest() {
//		when(keycloakService.getKidFromCerts()).thenReturn(kid);
//		Assertions.assertEquals(true, keycloakService.verifyToken(token));
//		
//		when(keycloakService.getKidFromCerts()).thenReturn(null);
//		Assertions.assertEquals(false, keycloakService.verifyToken(token));
	}

}
