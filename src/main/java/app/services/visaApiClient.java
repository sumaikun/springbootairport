package app.services;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.Base64;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import org.springframework.web.client.RestTemplate;

import org.apache.http.ssl.SSLContexts;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.HttpHost;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.core.io.ClassPathResource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


public class visaApiClient {
	
	
	
	private static String keystorePassword = "password";
    private static String keystorePath =  "myProject_keyAndCertBundle.jks";
    private static String privateKeyPassword = "password";
    
    private static String username = "WWRG1UUVKWQ9OBTLEV8N21ZvupUJRmhuXraAofRBPD9vGY2ho";
    private static String password = "jIYN1F0kQ2A";

	
	public static RestTemplate getRestTemplate() {
		
		System.out.println("on visa rest template");
		
        try {
        	
        	File file = new File("src/main/resources/"+keystorePath);
        	
        	keystorePath = file.getAbsolutePath();
        	
        	
        	System.out.println("key store path "+keystorePath);
        	
        	// Load client certificate into key store
        	SSLContext sslcontext = SSLContexts.custom()
        	        .loadKeyMaterial(new File(keystorePath), keystorePassword.toCharArray(),
        	        		privateKeyPassword.toCharArray())
        	        .loadTrustMaterial(new File(keystorePath), keystorePassword.toCharArray())
        	        .build();

        	// Allow TLSv1.2 protocol only
        	SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1.2" }, null,
        	        SSLConnectionSocketFactory.getDefaultHostnameVerifier());

        	CloseableHttpClient httpClient = HttpClients.custom()
        	            .setSSLSocketFactory(sslSocketFactory).build();
        	
        	
       
            
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

            requestFactory.setHttpClient(httpClient);
            RestTemplate restTemplate = new RestTemplate(requestFactory);         
            
            return restTemplate;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	public static HttpHeaders buildHttpHeaders()
	{
	    String notEncoded = username + ":" + password;
	    String encodedAuth = Base64.getEncoder().encodeToString(notEncoded.getBytes());
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.add("Authorization", "Basic " + encodedAuth);
	    return headers;
	}

}
