package app.models;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cities")
public class City implements Serializable {
	
	private static final long serialVersionUID = -7788619177798333712L; 

	  @Id
	  public String id;
	  
	  @NotNull
	  @Indexed(unique=true)
	  public String name;
	  
	  @NotNull	  
	  public String country;
	  
	   public String getId() {
			return id;
		}
	
		public void setId(String id) {
			this.id = id;
		}
	
		public String getName() {
			return name;
		}
	
		public void setName(String name) {
			this.name = name;
		}
		
		public String getCountry() {
			return country;
		}
	
		public void setCountry(String country) {
			this.country = country;
		}

}
