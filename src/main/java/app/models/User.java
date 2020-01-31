package app.models;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "users")

@JsonPropertyOrder({"id", "username", "password", "name", "secondname", "email", "isAdmin"})

public class User implements Serializable{
	
	 private static final long serialVersionUID = -7788619177798333712L;
	
	  @Id
	  public String id;
	  
	  @NotNull
	  @Indexed(unique=true)
	  public String username;	  
	  public String password;	  
	  @NotNull
	  public String name;
	  @NotNull
	  public String secondname;
	  @NotNull
	  @Indexed(unique=true)
	  public String email;
	  @NotNull
	  public Boolean isAdmin;
	  
	  public String[] permissions;
	  
	  public String[] getPermissions() {
		return permissions;
	  }
	
	  public void setPermissions(String[] permissions) {
		this.permissions = permissions;
	  }

	  @CreatedDate	
	  private Date createdDate;
	  
	  public Date getCreatedDate() {
		return createdDate;
	  }
	
	  public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
	  }
		
	  public Date getLastModifiedDate() {
			return lastModifiedDate;
	  }
		
	  public void setLastModifiedDate(Date lastModifiedDate) {
			this.lastModifiedDate = lastModifiedDate;
	  }

	  @LastModifiedDate	
	  private Date lastModifiedDate;

	  public User() {}

	  public User(String username, String password, String name,
			  String secondname, String email) {
		  
		    this.username = username;
		    this.password = password;
		    this.name = name;
		    this.secondname = secondname;
		    this.email = email;		   
		    
	  }
	  
	  public String getPassword() {

		 return this.password;

	  }
	  
	  public void setPassword(String password) {

		 this.password = password;

	  }

	  public void setUserId(String password) {
		
		 this.password = password;
		
	  }

	  @Override
	  public String toString() {
	    return String.format(
	        "User[id=%s, username='%s', name='%s', secondname='%s', email='%s']",
	        id, username, name, secondname, email);
	  }
	  
	  public String getId() {
			return id;
	  }

		public void setId(String id) {
			this.id = id;
		}
		
		public String getUsername() {
			return username;
		}
		
		public void setUsername(String username) {
			this.username = username;
		}
		
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public String getSecondname() {
			return secondname;
		}
		
		public void setSecondname(String secondname) {
			this.secondname = secondname;
		}
		
		public String getEmail() {
			return email;
		}
		
		public void setEmail(String email) {
			this.email = email;
		}
		
		public Boolean getIsAdmin() {
			return isAdmin;
		}
		
		public void setIsAdmin(Boolean isAdmin) {
			this.isAdmin = isAdmin;
		}

}
