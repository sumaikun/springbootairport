package app.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.dao.imp.BusinessEventsServices;
import app.dao.imp.UserServices;
import app.models.BusinessEvent;
import app.models.User;

@RestController

@RequestMapping("businessEvents")
public class BusinessEventController {
	
	private final BusinessEventsServices businessEventsServices;
	
	public BusinessEventController(BusinessEventsServices businessEventsServices) {

        this.businessEventsServices = businessEventsServices;

    }
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.GET)    

    public ResponseEntity<List<BusinessEvent>> allBusinessEvents(){ 

        return ResponseEntity.ok(businessEventsServices.findAll());
        
    }
	 
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/filterDates",method = RequestMethod.POST)    

	public ResponseEntity<List<BusinessEvent>> DatesBusinessEvents(@RequestBody Map<String, ?> input){ 
	
	    return ResponseEntity.ok(businessEventsServices.findByDate((String)input.get("startDate"), (String)input.get("endDate")));
	    
	}

}
