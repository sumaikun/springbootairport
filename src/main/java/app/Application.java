package app;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import app.services.RestServices;


@SpringBootApplication
public class Application implements CommandLineRunner{
	
	private RestServices restServices;
	
	public Application(RestServices restServices) {
        this.restServices = restServices;
    }

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		System.out.println("application main");
		
	}
	
	@Override
	public  void run(String... args) throws Exception {		

		
		//testing visa connection
		/*try {
		     JSONObject jsonObject = new JSONObject(restServices.testVisaApi());
		     String  responseBody = (String) jsonObject.get("body");
		     JSONObject jsonResponseBody = new JSONObject(responseBody);
		     System.out.println(jsonResponseBody.get("message"));
		}catch (JSONException err){
			 System.out.println("Error "+err.toString());
		}
		
		//testing visa validation
		try {
			JSONObject jsonObject = new JSONObject(restServices.validateVisaCard());
			String  responseBody = (String) jsonObject.get("body");
		    JSONObject jsonResponseBody = new JSONObject(responseBody);
		    
		    if(jsonResponseBody.has("approvalCode"))
		    {
		    	System.out.println(jsonResponseBody.get("approvalCode")+" valid card ");
		    }
		    else {
		    	System.out.println(" no valid card ");
		    }
		    
			
		}catch (JSONException err){
			 System.out.println("Error "+err.toString());
		}
		
		//testing visa inquiry data
		try {
		     JSONObject jsonObject = new JSONObject(restServices.inquiryVisaCard());
		     String  responseBody = (String) jsonObject.get("body");
		     JSONObject jsonResponseBody = new JSONObject(responseBody);
		     System.out.println(jsonResponseBody.get("cardProductId"));
		}catch (JSONException err){
			 System.out.println("Error "+err.toString());
		}*/
		
		
	}
}
