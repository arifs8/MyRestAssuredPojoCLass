package files;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DynamicJson {

	@Test(dataProvider = "BookData")
	public void addBook(String isbn, String aisle) {

		RestAssured.baseURI = "http://216.10.245.166";

		String resposneOfBody = given().log().all().header("Content-Type", "application/json")
				.body(payload.addBook(isbn, aisle))

				.when().post("Library/Addbook.php").then().log().all().assertThat().statusCode(200).extract().response()
				.asString();

		JsonPath jsp = ReUsableMethods.rawTojson(resposneOfBody);
		String id = jsp.get("ID");
		System.out.println("The output of the String Id is :" + id);

	}

	@DataProvider(name = "BookData")
	public Object[][] getData() {

		return new Object[][] { { "Syed", "1234" }, { "Aakib", "4567" }, { "Idris", "7890" } };

	}

}
