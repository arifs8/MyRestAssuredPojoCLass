import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.testng.Assert;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.LoginDetailsEcommerce;
import pojo.LoginResponseEcommerce;
import pojo.WebAutomation;
import pojo.orderDetails;
import pojo.orders;


public class EcommerceTest {

	public static void main(String[] args) {
		
		
	RequestSpecification reqSpec=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
			.setContentType(ContentType.JSON).build();
	
	
	LoginDetailsEcommerce lde = new LoginDetailsEcommerce();
	lde.setUserEmail("arifsyed83@gmail.com");
	lde.setUserPassword("Syedaakibidris@786");
	
	
	
	RequestSpecification reqLog =given().relaxedHTTPSValidation().log().all().spec(reqSpec).body(lde);
	LoginResponseEcommerce lse=  reqLog.when().log().all().post("api/ecom/auth/login").then().extract().response().as(LoginResponseEcommerce.class);
	System.out.println("The response token for login is :" + lse.getToken());
	String token = lse.getToken();
	System.out.println("The user Id is : "+ lse.getUserId());
	String userid= "lse.getUserId()";
	System.out.println("The response message is :" + lse.getMessage());
	
	
	
	//Add Product
	
	  RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token).build();
	  
	 RequestSpecification reqAddProduct =  given().log().all().spec(addProductBaseReq)
	  	.param("productName", "qwerty")
	  	.param("productAddedBy", userid)
	  	.param("productCategory", "fashion")
	  	.param("productSubCategory", "shirts")
	  	.param("productPrice", "11500")
	  	.param("productDescription", "Addias Originals")
	  	.param("productFor", "women")
		.multiPart("productImage",new File("C://Users//UIE13841//Downloads//WhatsApp Image 2024-01-24 at 4.51.31 PM.jpeg"));
	 
	 String addProduct = reqAddProduct.when().log().all().post("/api/ecom/product/add-product").then().extract().response().asString();
	 JsonPath jp = new JsonPath(addProduct);
	 String productId =jp.get("productId");
	 System.out.println("The product id is : "+ productId);
	
	
		//CreateOrder
	  RequestSpecification createOrderSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token).setContentType(ContentType.JSON).build();
	  
	  orderDetails ord = new orderDetails();
	  ord.setCountry("India");
	  ord.setProductOrderedId(productId);
	  
	  List<orderDetails> orderList = new ArrayList<orderDetails>();
	  orderList.add(ord);
	  
	  orders od = new orders();
	  od.setOrder(orderList);
	  
	 RequestSpecification createOrder = given().log().all().spec(createOrderSpec).body(od);
	  
	String orderResponse=  createOrder.when().post("/api/ecom/order/create-order").then().extract().response().asString();	
	System.out.println("The order response is :" +orderResponse);
	
	
	
	//Delete Product
	
	RequestSpecification deleteProdBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token).setContentType(ContentType.JSON).build();
	
	 RequestSpecification deleteProdReq =  given().log().all().spec(deleteProdBaseReq).pathParam("productId", productId);
	 
	 
	  String deleteProductResponse = deleteProdReq.when().delete("api/ecom/product/delete-product/{productId}").then().log().all().extract().response().asString();
	  
	  JsonPath jspb = new JsonPath(deleteProductResponse);
	  System.out.println(jspb.get("message"));
	 
	}
	

	

}
