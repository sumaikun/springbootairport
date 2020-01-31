package app.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.dao.RestDao;
import app.dao.imp.CitiesServices;
import app.dao.imp.CountriesServices;
import app.models.City;
import app.models.Country;

@RestController

@RequestMapping("cities")
public class CitiesController extends RestMasterController {

	private final CitiesServices citiesService;
	
	public CitiesController(CitiesServices citiesService) {
		super(citiesService);
		// TODO Auto-generated constructor stub
		this.citiesService = citiesService;
	}	
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method=RequestMethod.POST)   

    public  ResponseEntity<Object> save(@RequestBody @Valid City city){    	  	 
    	 
         return ResponseEntity.ok(citiesService.save(city));

    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/{id}",method=RequestMethod.PUT)   

    public ResponseEntity<Void> update(@RequestBody @Valid City city,
    		@PathVariable String id){	    	
    	 
    	 citiesService.update(city,id);
    	 
    	 return ResponseEntity.ok().build();

    }  
	
	
	
     

}
