import files.payload;
import io.restassured.path.json.JsonPath;

public class CompleteJsonParse {

	public static void main(String[] args) {

		JsonPath js = new JsonPath(payload.CoursePrice());
		int count = js.get("courses.size()");
		System.out.println("The number of cources are : " + count);

		int purAmount = js.get("dashboard.purchaseAmount");
		System.out.println("The purchase amount of the dashbors is: " + purAmount);

		String firstName = js.get("courses[0].title");
		System.out.println("The title of the First Cources is: " + firstName);

		String secondName = js.get("courses[1].title");
		System.out.println("The title of the Second Cources is: " + secondName);

		String thirdName = js.get("courses[2].title");
		System.out.println("The title of the Third Cources is: " + thirdName);

		for (int i = 0; i < count; i++) {

			String courcesNames = js.get("courses[" + i + "].title");

			System.out.println(js.get("courses[" + i + "].price").toString());
			System.out.println(courcesNames);
		}

		for (int i = 0; i < count; i++) {

			String courcesNames = js.get("courses[" + i + "].title");

			if(courcesNames.equalsIgnoreCase("RPA")) {
				
				System.out.println( "The num of copies of RPA is : " + js.get("courses[" + i + "].copies"));
				break;
			}
		}

	}

}
