import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class serialization {

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
		
	Response res=	given().log().all().queryParam("key", "qaclick123")
		.body(ap)
		.when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).extract().response();
		
		String resString = res.asString();
		System.out.println(resString);
		

	}

}
