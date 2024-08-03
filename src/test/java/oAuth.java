import static io.restassured.RestAssured.given;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.testng.Assert;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

public class oAuth {

	public static void main(String[] args) throws InterruptedException {

		// TODO Auto-generated method stub

		String response =

				given()

						.formParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
						.formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
						.formParams("grant_type", "client_credentials")
						.formParams("scope", "trust")
						.when().log().all()
						.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();

		System.out.println(response);
		JsonPath jsonPath = new JsonPath(response);
		String accessToken = jsonPath.getString("access_token");
		System.out.println("The Access token is : " + accessToken);
		
		GetCourse gc = given()
				.queryParams("access_token", accessToken)
				.when()
				.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
				.as(GetCourse.class);
		System.out.println(gc.getInstructor());
		System.out.println( gc.getCourses().getApi().get(0).getPrice() );
		
		List<Api> apiCources = gc.getCourses().getApi();
		for(int i=0; i<apiCources.size() ;i++) {
			if(apiCources.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")){
				
				System.out.println("The price of Soap Ui is: "+ apiCources.get(i).getPrice());
			}
		}
		
		
		String[] courseTitle = {"Selenium Webdriver Java" , "Cypress" , "Protractor","Arif"};
		ArrayList<String> arr = new ArrayList<String>();
		
		List<WebAutomation> webAuto = gc.getCourses().getWebAutomation();
		for(int i=0 ;i<webAuto.size() ;i++) {
			
			if(webAuto.get(i)!=null) {
				
				System.out.println(webAuto.get(i).getCourseTitle());
				arr.add(webAuto.get(i).getCourseTitle());
			}
		}
		
		List<String> expectedList = Arrays.asList(courseTitle);
		Assert.assertTrue(arr.equals(expectedList));
		

	}

}
