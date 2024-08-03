import org.testng.Assert;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {
	
	
	@Test
	public void SumOfCources() {
		
		JsonPath js = new JsonPath(payload.CoursePrice());
		
		int count = js.get("courses.size()");
		int sum =0;
		for(int i=0; i<count ;i++) {
			
		int priceCount =js.get("courses["+i+"].price");
		int copiesCount = js.get("courses["+i+"].copies");
		
		int amount = priceCount * copiesCount;
		System.out.println("The total amount present is: "+amount);
		sum = sum+amount;
		
		}
		
		System.out.println("Total sum : " + sum);
		
		int purchaseAmount=  js.get("dashboard.purchaseAmount");
		System.out.println(purchaseAmount);
		
		Assert.assertEquals(sum, purchaseAmount);
	}
	

}
