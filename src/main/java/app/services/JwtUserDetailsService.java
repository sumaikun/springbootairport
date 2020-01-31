package app.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import app.repository.imp.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	private UserRepository userRepository;
	
	public JwtUserDetailsService(UserRepository userRepository){

        this.userRepository = userRepository;

    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		//System.out.println("here is the username");
		
		//System.out.println(username);
		
		//superadmins login
		if ("javainuse".equals(username)) {
			
			ArrayList<GrantedAuthority>rolesList = new ArrayList<>();
			
			rolesList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			
			return new User("javainuse", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
					rolesList );
		} else {
			
			System.out.println("it is no javainuse");
			
			app.models.User data  = userRepository.findByUsername(username);
			
			System.out.println("user details");
			
			System.out.println(data.getPassword());
			
			if(data != null)
			{
				return buildUserForAuthentication(data);
			}
			else {
				throw new UsernameNotFoundException("User not found with username: " + username);
			}
			
			
		}
	}
	
	private UserDetails buildUserForAuthentication(app.models.User user) {
		
		List<GrantedAuthority> roles =new ArrayList<>();
		
		System.out.println(user.getPermissions());
		
		if(user.getPermissions() == null)
		{
			String[] voidA = new String[0];
			user.setPermissions(voidA);
		}
		
		if(user.getIsAdmin() && user.getPermissions().length == 0)
		{
			roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		
		if(user.getIsAdmin() && user.getPermissions().length > 0)
		{
			roles.add(new SimpleGrantedAuthority("ROLE_BOSS"));
		}
		
		if(!user.getIsAdmin())
		{
			roles.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
		
	    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),roles);
	}
}


/*
 * 
 * 
 * if(user.isAdmin && (  user.getPermissions() == null || user.getPermissions().length == 0 ))
		{
			roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		
		else if(user.isAdmin && ( user.getPermissions() != null || user.getPermissions().length > 0 ))
		{
			roles.add(new SimpleGrantedAuthority("ROLE_BOSS"));
		}
		
		else if( !user.isAdmin ) {
			roles.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
 * 
 * 
 * */
 