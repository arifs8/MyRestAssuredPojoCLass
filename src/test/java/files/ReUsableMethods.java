package files;

import io.restassured.path.json.JsonPath;

public class ReUsableMethods {
	
	public static JsonPath rawTojson(String response) {
		
		JsonPath js1 = new JsonPath(response);
		return js1;
	}

}
