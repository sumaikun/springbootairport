package app.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.models.BusinessEvent;
import app.services.DragonServices;
import app.dao.imp.BusinessEventsServices;
import app.enums.*;


@RestController

@RequestMapping("pass")

public class DragonController {
	
	private final BusinessEventsServices businessEventsServices;
	
	@Resource(name = "dragonServices")
    DragonServices dragonServices;
	
	public DragonController(BusinessEventsServices businessEventsServices) {
		this.businessEventsServices = businessEventsServices;
	}
	
	@RequestMapping(value="/validateCard",method=RequestMethod.POST)   

    public  ResponseEntity<?> validateCard(@RequestBody Map<String, ?> input){
			
		ResponseEntity<String> queryResponse = dragonServices.queryCardNumber((String)input.get("cardno"),
				(String)input.get("loungeid"));
		
		JSONObject jsonObject = new JSONObject(queryResponse);
		
		String  responseBody = (String) jsonObject.get("body");
		
		JSONObject jsonResponseBody = new JSONObject(responseBody);
		
		System.out.println(jsonResponseBody.toString());
		
		Map<String, Object> responseObj = new HashMap<String, Object>();
		
		if(Integer.parseInt((String) jsonResponseBody.get("result")) == 0)
		{
			responseObj.put("validation",true);
			responseObj.put("partners",(String)jsonResponseBody.get("partners"));
			saveBusinessEvent((String)input.get("cardno"),
			(String)input.get("loungeid"),(String) jsonResponseBody.get("name"));
		}
		
		else if(Integer.parseInt((String)jsonResponseBody.get("result")) ==  1)
		{
			responseObj.put("validation",false);
			
			String resmessage = (String)jsonResponseBody.get("message");
			
			if(resmessage.contains("120"))
			{
				responseObj.put("validation",true);
				responseObj.put("message","Por favor continue");
			}
			else {
				
				rejectBusinessEvent((String)input.get("cardno"),
						(String)input.get("loungeid"));
			
			}
				
			
		}
		
		else {
			
			responseObj.put("validation",false);
			rejectBusinessEvent((String)input.get("cardno"),
					(String)input.get("loungeid"));
		}
		
		return new ResponseEntity<Object>(responseObj, HttpStatus.OK);

    }
	
	@RequestMapping(value="/orderEntry",method=RequestMethod.POST)
	public  ResponseEntity<?> OrderEntry(@RequestBody Map<String, ?> input){
		
		ResponseEntity<String> queryResponse = dragonServices.orderCardEntry((String)input.get("cardno"),
				(String)input.get("loungeid"), (String)input.get("adultcount"), (String)input.get("childcount"));		
		
		JSONObject jsonObject = new JSONObject(queryResponse);
		String  responseBody = (String) jsonObject.get("body");
		JSONObject jsonResponseBody = new JSONObject(responseBody);
		
		System.out.println(jsonResponseBody.toString());
		
		Map<String, Object> responseObj = new HashMap<String, Object>();
    	 
		if(Integer.parseInt((String) jsonResponseBody.get("result")) == 0)
		{
			responseObj.put("validation",true);
			responseObj.put("transsequno",(String)jsonResponseBody.get("transsequno"));
			
			orderBusinessEvent((String)input.get("cardno"),
					(String)input.get("loungeid"),
					(String)jsonResponseBody.get("transsequno"),
					(String)input.get("adultcount"),
					(String)input.get("childcount")
					);
		}
		
		else if(Integer.parseInt((String)jsonResponseBody.get("result")) ==  1)
		{
			responseObj.put("validation",false);			
		}
		
		else {
			
			responseObj.put("validation",false);
		}
		
		return new ResponseEntity<Object>(responseObj, HttpStatus.OK);

    }
	
	@RequestMapping(value="/cancelEntry",method=RequestMethod.POST)
	public  ResponseEntity<?> CancelEntry(@RequestBody Map<String, ?> input){
		
		BusinessEvent businessEvent = businessEventsServices.findByCard((String)input.get("cardno"));
		
		String loungeid = businessEvent.getLounge();
		
		String transsequno = businessEvent.getTransaction(); 
		
		ResponseEntity<String> queryResponse = dragonServices.cancelCardEntry((String)input.get("cardno"),
				loungeid, transsequno);		
		
		JSONObject jsonObject = new JSONObject(queryResponse);
		String  responseBody = (String) jsonObject.get("body");
		JSONObject jsonResponseBody = new JSONObject(responseBody);
		
		System.out.println(jsonResponseBody.toString());
    	 
		Map<String, Object> responseObj = new HashMap<String, Object>();
   	 
		if(Integer.parseInt((String) jsonResponseBody.get("result")) == 0)
		{
			responseObj.put("validation",true);	
			
			cancelBusinessEvent((String)input.get("cardno"));
		}
		
		else if(Integer.parseInt((String)jsonResponseBody.get("result")) ==  1)
		{
			responseObj.put("validation",false);
			
			long diff = new Date().getTime() - businessEvent.getCreatedDate().getTime();			
			
			if( (diff/(1000*60)) >= 30  ) {
				responseObj.put("message","Han pasado mas de 30 minutos, no puede cancelar la operación");
			}
			
		}
		
		else {
			
			responseObj.put("validation",false);
		}
		
		return new ResponseEntity<Object>(responseObj, HttpStatus.OK);

    }
	
	
	public void saveBusinessEvent(String cardno , String loungeid, String name)
	{
		if(!validateRegister(cardno))
		{
			BusinessEvent businessEvent = new BusinessEvent();
			
			businessEvent.setCustomer(name);
			
			businessEvent.setCard(cardno);
			
			businessEvent.setLounge(loungeid);
			
			businessEvent.setState(EventState.QUERY);
			
			businessEventsServices.saveBusinessEvent(businessEvent);
		}		
	}
	
	public void orderBusinessEvent(String cardno, String loungeid, String transsequno,
			String adultcount, String childcount)
	{
		BusinessEvent businessEvent = businessEventsServices.findByCard(cardno);
		businessEvent.setTransaction(transsequno);
		businessEvent.setAdultC(adultcount);
		businessEvent.setChildC(childcount);
		businessEvent.setState(EventState.ORDER);
		businessEventsServices.updateBusinessEvent(businessEvent, businessEvent.getId());
	}
	
	public void cancelBusinessEvent(String cardno)
	{
		BusinessEvent businessEvent = businessEventsServices.findByCard(cardno);
		
		businessEvent.setState(EventState.CANCEL);
		
		businessEventsServices.updateBusinessEvent(businessEvent, businessEvent.getId());
	}
	
	public void rejectBusinessEvent(String cardno , String loungeid)
	{
		if(!validateRegister(cardno))
		{
			BusinessEvent businessEvent = new BusinessEvent();
			
			businessEvent.setCard(cardno);
			
			businessEvent.setLounge(loungeid);
			
			businessEvent.setState(EventState.REJECT);
			
			businessEventsServices.saveBusinessEvent(businessEvent);
		}
	}
	
	public Boolean validateRegister(String cardno)
	{
		BusinessEvent businessEvent = businessEventsServices.findByCard(cardno);
		if(businessEvent != null)
		{
			//System.out.println(businessEvent.getCreatedDate());
			
			Date now = new Date();
			
			//System.out.println(now);
			
			long diff = now.getTime() - businessEvent.getCreatedDate().getTime();
			
			//System.out.println(diff/(1000*60));
			
			//System.out.println("Difference between is "
			  //      + (diff / (1000 * 60 * 60 * 24)) + " days.");
			
			if( ( diff/( 1000*60 ) ) >= 120  ) {
				return false;
			}
			
			return true;
		}
		return false;
	}
	
	

}
