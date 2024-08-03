import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilder {

	public static void main(String[] args) {

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		AddPlace ap = new AddPlace();
		ap.setAccuracy(50);
		ap.setAddress("29, side layout, cohen 09");
		ap.setName("Frontline house");
		ap.setPhone_number("+91 9566208229");
		ap.setWebsite("www.arifsyed.in");
		ap.setLanguage("English");

		List<String> myList = new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");

		ap.setTypes(myList);

		Location lp = new Location();
		lp.setLat(-31.12345);
		lp.setLng(13.23456);

		ap.setLocation(lp);
		
		
		//here in the below we are providing Request Specification Builder
		RequestSpecification rsb = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();
		
		
		
		RequestSpecification resp =  given().log().all().spec(rsb).body(ap);
		
		//Here in the below we are creating response specification builder
		
		ResponseSpecification respSpec= new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
			Response rep =	resp.when().post("maps/api/place/add/json")
				.then().log().all().spec(respSpec).extract().response();

		//Response res = given().log().all().queryParam("key", "qaclick123").body(ap).when()
				//.post("maps/api/place/add/json").then().log().all().assertThat().statusCode(200).extract().response();

		String resString = rep.asString();
		System.out.println(resString);

	}

}
