package app.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import app.models.Greeting;
import app.services.RestServices;

@RestController
public class VisaController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	private RestServices restServices;
	
	public VisaController(RestServices restServices) {
		this.restServices = restServices;
	}
	
	

	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
		return new Greeting(counter.incrementAndGet(),
							String.format(template, name));
	}
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@PostMapping(value = "/inquiryAndValidateCard", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> inquiryAndValidateCard(@RequestBody Map<String, ?> input) 
	{
		 System.out.println("inquiry and validate card data on endpoint");
		 
		 Map<String, Object> responseObj = new HashMap<String, Object>();		 
		 
		 try {			 
				 ResponseEntity<String> visaValidation = restServices.validateVisaCard((String) input.get("cardCvv2Value"),
						 (String) input.get("cardExpiryDate"),
						 (String) input.get("primaryAccountNumber"));
				
				 System.out.println(visaValidation);
				 
				 if(visaValidation == null)
				 {
					 responseObj.put("validation", false);
				 }
				 else {
					 JSONObject jsonObject = new JSONObject(visaValidation);
					 String  responseBody = (String) jsonObject.get("body");
					 JSONObject jsonResponseBody = new JSONObject(responseBody);
					    
					 if(jsonResponseBody.has("approvalCode"))
					 {
				    	System.out.println(jsonResponseBody.get("approvalCode")+" valid card ");	
				    	
				    	System.out.println("Número de tarjeta "+(String) input.get("primaryAccountNumber"));
				    	
				    	jsonObject = new JSONObject(restServices.inquiryVisaCard((String) input.get("primaryAccountNumber")));
					    responseBody = (String) jsonObject.get("body");
					    jsonResponseBody = new JSONObject(responseBody);
					    responseObj.put("validation", true);
					    
					 
					    
					    responseObj.put("cardProductId", jsonResponseBody.get("cardProductId").toString());
					    responseObj.put("cardProductName", jsonResponseBody.get("cardProductName").toString());
					    responseObj.put("cardTypeCode", jsonResponseBody.get("cardTypeCode").toString());
					    responseObj.put("issuerName", jsonResponseBody.get("issuerName").toString());
					    responseObj.put("countryCode", jsonResponseBody.get("countryCode").toString());
					    
					 }
					 else {
				    	System.out.println(" no valid card ");
				    	responseObj.put("validation", false);				    	
					 }
					     
				 }			 
			
				
			}catch (JSONException err){
				 System.out.println("Error "+err.toString());
				
			}
		 
		 //this.AppLoginResponse((String)input.get("username"),(String)input.get("password"));	 
	        
	     return ((responseObj == null) ? new ResponseEntity(responseObj, HttpStatus.NO_CONTENT) : new ResponseEntity(responseObj, HttpStatus.OK));		 
		   
	}
}
