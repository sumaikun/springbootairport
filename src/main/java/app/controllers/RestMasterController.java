package app.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import app.dao.RestDao;

public class RestMasterController {
	
	
	private final RestDao restDao;
	
	public RestMasterController(RestDao restDao)
	{
		this.restDao = restDao;
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/{id}",method = RequestMethod.GET)

    public ResponseEntity<?> getById(@PathVariable String id){
       

    	Object data = restDao.findById(id);
    	
    	if(data == null)
    	{
    		return (ResponseEntity<?>) ResponseEntity.noContent();
    	}
    	else {
    		return ResponseEntity.ok(data);
    	}

    }
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET)    

    public ResponseEntity<List<Object>> getAll(){ 

        return ResponseEntity.ok(restDao.findAll());
        
    }
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)   

    public ResponseEntity<Void> delete(@PathVariable String id){

		restDao.delete(id);

        return ResponseEntity.ok().build();

    }



}
