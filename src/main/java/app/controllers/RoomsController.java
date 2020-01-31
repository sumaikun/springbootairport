package app.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import app.dao.imp.RoomsService;
import app.models.Rooms;
import app.services.FileStorageService;

@RestController


@RequestMapping("rooms")
public class RoomsController extends RestMasterController{
	
	private final RoomsService roomsService;
	
	@Autowired
    private FileStorageService fileStorageService;
	
	private HttpServletRequest request;

	public RoomsController(RoomsService roomsService,HttpServletRequest request) {
		super(roomsService);
		// TODO Auto-generated constructor stub
		this.roomsService = roomsService;
		this.request = request;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method=RequestMethod.POST)   

    public  ResponseEntity<Object> save(@RequestBody @Valid Rooms rooms){    	  	 
    	 
         return ResponseEntity.ok(roomsService.save(rooms));

    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/{id}",method=RequestMethod.PUT)   

    public ResponseEntity<Void> update(@RequestBody @Valid Rooms rooms,
    		@PathVariable String id){	    	
    	 
    	 roomsService.update(rooms,id);
    	 
    	 return ResponseEntity.ok().build();

    }
    
    @Override
    @InitBinder
    @PreAuthorize("permitAll")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Object>> getAll(){ 
    	
    	String token = request.getHeader("Authorization").replace("Bearer ", "");    	
    	
    	System.out.println(token);
    	
        return ResponseEntity.ok(roomsService.findAll(token));
        
    }
     
    

}
