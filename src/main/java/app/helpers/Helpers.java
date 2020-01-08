package app.helpers;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;

import org.xml.sax.InputSource;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Helpers {
	
	public static String getMsgType(String message) {
	    try {
	        new ObjectMapper().readTree(message);
	        System.out.println("Message is valid JSON.");
	        return "JSON";
	    } catch (IOException e) {
	    	System.out.println("Message is not valid JSON.");
	    }

	    try {
	        DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(message)));
	        System.out.println("Message is valid XML.");
	        return "XML";
	    } catch (Exception e) {
	    	System.out.println("Message is not valid XML.");
	    }

	    return null;
	}

}
