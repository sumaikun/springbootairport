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
import app.dao.imp.CountriesServices;
import app.models.Country;
import app.models.User;

@RestController

@RequestMapping("countries")
public class CountryController extends RestMasterController{
	
	private final CountriesServices countryService;
	
	public CountryController(CountriesServices countryService) {
		super(countryService);
		// TODO Auto-generated constructor stub
		
		this.countryService = countryService;
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method=RequestMethod.POST)   

    public  ResponseEntity<Object> save(@RequestBody @Valid Country country){    	  	 
    	 
         return ResponseEntity.ok(countryService.save(country));

    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/{id}",method=RequestMethod.PUT)   

    public ResponseEntity<Void> update(@RequestBody @Valid Country country,
    		@PathVariable String id){	
    	
    	 
    	 countryService.update(country,id);
    	 
    	 return ResponseEntity.ok().build();

    }  
	
	

}
