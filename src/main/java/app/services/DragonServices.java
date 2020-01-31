package app.services;

import java.time.Duration;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import app.config.JwtTokenUtil;

public class DragonServices {
	
	private final RestTemplate restTemplate;
	private String token;
	private HttpHeaders headers;
	
	@Value("${dragon.url}")
	private String dragonUrl;
	
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;	
	
	
	public DragonServices(){
		 
		 RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
		 
		 this.restTemplate = restTemplateBuilder
	                .setConnectTimeout(Duration.ofSeconds(500))
	                .setReadTimeout(Duration.ofSeconds(500))
	                .build();			
	}
	
	
	private  void ValidateTokenOrReload()
	{
		
		if(!jwtTokenUtil.validateDragonToken(this.token))
		{
			this.token = jwtTokenUtil.generateDragonToken();
			this.GenerateHeaders(token);
		}
		else {
			System.out.println("Valid Token");
		}
	}
	
	
	private void GenerateHeaders(String token) {		

		this.headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer " + token);
		
	}
	
	
	public ResponseEntity<String> queryCardNumber(String cardno, String loungeid){
		
		this.ValidateTokenOrReload();
		
		StringBuilder url = new StringBuilder();
		
		url.append(dragonUrl);
		
		url.append("/pos/posQuery");
		
		Map<String, Object> map = new HashMap<>();
    	
        map.put("cardno", cardno);
        
        map.put("loungeid", loungeid);
		
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map , this.headers);
        
        return this.restTemplate.exchange(url.toString(), HttpMethod.POST, entity , String.class);		
		
	}
	
	public ResponseEntity<String> orderCardEntry(String cardno, String loungeid, String adultcount, String childcount){
		
		this.ValidateTokenOrReload();
		
		StringBuilder url = new StringBuilder();
		
		url.append(dragonUrl);
		
		url.append("/pos/posOrder");
		
		Map<String, Object> map = new HashMap<>();
    	
        map.put("cardno", cardno);
        
        map.put("loungeid", loungeid);
        
        map.put("adultcount", adultcount);
        
        map.put("childcount", childcount);
		
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map , this.headers);
        
        return this.restTemplate.exchange(url.toString(), HttpMethod.POST, entity , String.class);		
		
	}
	
	public ResponseEntity<String> cancelCardEntry(String cardno, String loungeid, String transsequno){
		
		this.ValidateTokenOrReload();
		
		StringBuilder url = new StringBuilder();
		
		url.append(dragonUrl);
		
		url.append("/pos/posCancel");
		
		Map<String, Object> map = new HashMap<>();
    	
        map.put("cardno", cardno);
        
        map.put("loungeid", loungeid);
        
        map.put("transsequno", transsequno);        
    
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map , this.headers);
        
        return this.restTemplate.exchange(url.toString(), HttpMethod.POST, entity , String.class);		
		
	}

}
