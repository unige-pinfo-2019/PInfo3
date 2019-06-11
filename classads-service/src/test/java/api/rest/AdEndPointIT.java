package api.rest;

import org.junit.jupiter.api.BeforeAll;

import io.restassured.RestAssured;

public class AdEndPointIT {
	
	@BeforeAll
	public static void setup() {
		RestAssured.baseURI = "http://localhost:28080/classadsresponses";
		RestAssured.port = 8080;
	}


}
