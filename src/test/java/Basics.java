import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*; //Equals methods //under static library so eclipse wont give us

import org.testng.Assert;

import files.ReUsableMethods;
import files.payload;

public class Basics {

	public static void main(String[] args) {

		// given() - All input details;
		// when() = Submit the API resource http method
		// then()= to do the verification of the output by keeping assertions

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		String resposne = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(payload.addPlace())

				.when().post("maps/api/place/add/json")

				.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
				.header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();

		// Add place
		// Update the place with new Address
		// Get place to validate if new address is present in response of the body
		
		System.out.println(resposne);
		
		JsonPath jsp = ReUsableMethods.rawTojson(resposne);
		//JsonPath jsp = new JsonPath(resposne);
		String placeId = jsp.getString("place_id");
		System.out.println("The response body of the plavcve id is : " + placeId);
		
		
		//Update Place
		
		String newPlaceAddress ="#7A,MSp,KR pura";
		
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
			.body("{\r\n"
					+ "\"place_id\":\""+placeId+"\",\r\n"
					+ "\"address\":\""+newPlaceAddress+"\",\r\n"
					+ "\"key\":\"qaclick123\"\r\n"
					+ "}\r\n"
					+ "")
			//.body(new String (Files.readAllBytes(Path.get("XXXXXXXXXXXXXXXXXXXXXXpath location .json file XXXXXXXXX))))
			.when().put("maps/api/place/update/json")
			.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//Get call
		
		String getPlaceResponse = given().log().all().queryParam("place_id", placeId).queryParam("key", "qaclick123")
		.when().get("maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		System.out.println("This is response of get call : " + getPlaceResponse);
		
		JsonPath jsp1 = ReUsableMethods.rawTojson(getPlaceResponse);
		//JsonPath jsp1 = new JsonPath(getPlaceResponse);
		String actualString = jsp1.getString("address");
		System.out.println("The Actual place is  : " + actualString);
		
		Assert.assertEquals(actualString, newPlaceAddress);
		
		//content of the file to String
		//Content of file can convert to Byte
		//Then Byte to String
		
	}

}
