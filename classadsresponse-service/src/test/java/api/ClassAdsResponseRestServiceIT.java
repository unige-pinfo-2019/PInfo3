package api;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;

public class ClassAdsResponseRestServiceIT {

	@BeforeAll
	public static void setup() {
		RestAssured.baseURI = "http://localhost:28080/classadsresponses";
		RestAssured.port = 8080;
	}

	@Test
	public void testGetResponseByUserAndAd() {
		when().get("/users/1/ads/0?offset=0&limit=10").then().body(containsString("Message1"));

	}
	
	@Test
	public void testGetResponsesByUser() {
		when().get("/users/1").then().body(containsString("Message5"));
		when().get("/users/2").then().body(containsString("Message2"));
		when().get("/users/2").then().body(containsString("Message4"));
	}

}