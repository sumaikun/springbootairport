package app.services;


import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;


import java.time.Duration;
import java.util.*;

@Service
public class RestServices{
	
	private final RestTemplate restTemplate;
	
	private final HttpHeaders visaHeaders =  visaApiClient.buildHttpHeaders();
	
	private final RestTemplate visaRestTemplate = visaApiClient.getRestTemplate();
	
	public RestServices(RestTemplateBuilder restTemplateBuilder) {
        // set connection and read timeouts
        this.restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(500))
                .setReadTimeout(Duration.ofSeconds(500))
                .build();
    }
	
	//NormalGetMethod
	public String getPostsPlainJSON() {
		 
	        String url = "https://jsonplaceholder.typicode.com/posts";
	        return this.restTemplate.getForObject(url, String.class);
	        
	}
	
	public ResponseEntity<String> testVisaApi(){       
        
		System.out.println("on visa test api function");
		
        try {        	     	
        	String url = "https://sandbox.api.visa.com/vdp/helloworld";
        	
        	HttpEntity<String> entity = new HttpEntity<>("body", visaHeaders);

            // use `exchange` method for HTTP call
            
        	return this.visaRestTemplate.exchange(url, HttpMethod.GET, entity , String.class);        	
        	
         	//return this.visaRestTemplate.getForObject(url, String.class);
         	
        } catch (HttpStatusCodeException ex) {
            // raw http status code e.g `404`
            System.out.println(ex.getRawStatusCode());
            // http status code e.g. `404 NOT_FOUND`
            System.out.println(ex.getStatusCode().toString());
            // get response body
            System.out.println(ex.getResponseBodyAsString());
            // get http headers
            HttpHeaders headers= ex.getResponseHeaders();
            System.out.println(headers.get("Content-Type"));
            System.out.println(headers.get("Server"));
        }

        return null;
        
	}
	
	
	public ResponseEntity<String> validateVisaCard(String cardCvv2Value , String cardExpiryDate,
			String primaryAccountNumber){
		
		System.out.println("on visa validation card function");
		
        try {        	     	
        	String url = "https://sandbox.api.visa.com/pav/v1/cardvalidation";
        	
        	Map<String, Object> map = new HashMap<>();
            map.put("cardCvv2Value", cardCvv2Value);
            map.put("cardExpiryDate", cardExpiryDate);
            map.put("primaryAccountNumber", primaryAccountNumber);
        	
        	HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map , visaHeaders);

            // use `exchange` method for HTTP call
            
        	return this.visaRestTemplate.exchange(url, HttpMethod.POST, entity , String.class);        	
        	
         	//return this.visaRestTemplate.getForObject(url, String.class);
         	
        } catch (HttpStatusCodeException ex) {
            // raw http status code e.g `404`
            System.out.println(ex.getRawStatusCode());
            // http status code e.g. `404 NOT_FOUND`
            System.out.println(ex.getStatusCode().toString());
            // get response body
            System.out.println(ex.getResponseBodyAsString());
            // get http headers
            HttpHeaders headers= ex.getResponseHeaders();
            System.out.println(headers.get("Content-Type"));
            System.out.println(headers.get("Server"));
        }

        return null;
		
		
	}
	
	public ResponseEntity<String> inquiryVisaCard(String primaryAccountNumber){
		
		System.out.println("on visa validation inquiry function");
		
        try {        	     	
        	String url = "https://sandbox.api.visa.com/paai/generalattinq/v1/cardattributes/generalinquiry";
        	
        	Map<String, Object> map = new HashMap<>();
        	
            map.put("primaryAccountNumber", primaryAccountNumber);
        	
        	HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map , visaHeaders);

            // use `exchange` method for HTTP call
            
        	return this.visaRestTemplate.exchange(url, HttpMethod.POST, entity , String.class);        	
        	
         	//return this.visaRestTemplate.getForObject(url, String.class);
         	
        } catch (HttpStatusCodeException ex) {
            // raw http status code e.g `404`
            System.out.println(ex.getRawStatusCode());
            // http status code e.g. `404 NOT_FOUND`
            System.out.println(ex.getStatusCode().toString());
            // get response body
            System.out.println(ex.getResponseBodyAsString());
            // get http headers
            HttpHeaders headers= ex.getResponseHeaders();
            System.out.println(headers.get("Content-Type"));
            System.out.println(headers.get("Server"));
        }

        return null;
		
	}

	
	
	
}