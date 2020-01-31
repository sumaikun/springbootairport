package app.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.dao.imp.UserServices;
import app.models.User;

@RestController

@RequestMapping("users")
public class UserController {
	
	private final UserServices userService;

    private User user;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    //@Autowired only have sense if userservice was an interface

    public UserController(UserServices userService) {

        this.userService = userService;

    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/{userId}",method = RequestMethod.GET)

    public ResponseEntity<?> userById(@PathVariable String userId){
       

    	user = userService.findByUserId(userId);
    	
    	if(user == null)
    	{
    		return (ResponseEntity<?>) ResponseEntity.noContent();
    	}
    	else {
    		return ResponseEntity.ok(user);
    	}  


    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET)    

    public ResponseEntity<List<User>> allUsers(){ 

        return ResponseEntity.ok(userService.findAll());
        
    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/{userId}",method = RequestMethod.DELETE)   

    public ResponseEntity<Void> deleteUser(@PathVariable String userId){

    	userService.deleteUser(userId);

        return ResponseEntity.ok().build();

    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method=RequestMethod.POST)   

    public  ResponseEntity<User> saveUser(@RequestBody @Valid User user){
    	
    	 if(user.getPassword() != null)
    	 {
    		 user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    	 }    	 
    	 
         return ResponseEntity.ok(userService.saveUser(user));

    }
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/{userId}",method=RequestMethod.PUT)   

    public ResponseEntity<Void> updateUser(@RequestBody @Valid User user, @PathVariable String userId){
    	
    	 if(user.getPassword() != null)
    	 {
    		 user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    	 }
    	 
    	 userService.updateUser(user,userId);
    	 
    	 return ResponseEntity.ok().build();

    }   
    
    

}
