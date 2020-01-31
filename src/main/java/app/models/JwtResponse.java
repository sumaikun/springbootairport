package app.models;


import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
public class JwtResponse implements Serializable {
	
	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private final Collection<? extends GrantedAuthority> role;
	
	public JwtResponse(String jwttoken,Collection<? extends GrantedAuthority> collection) {
		this.jwttoken = jwttoken;
		this.role = collection;
	}
	
	public String getToken() {
		return this.jwttoken;
	}
	
	public Collection<? extends GrantedAuthority> getRole() {
		return this.role;
	}
}
