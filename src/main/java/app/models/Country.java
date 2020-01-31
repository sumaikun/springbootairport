package app.models;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "countries")
public class Country implements Serializable{	
	
	private static final long serialVersionUID = -7788619177798333712L;
	
	 @Id
	  public String id;
	 
	 @NotNull
	  @Indexed(unique=true)
	  public String name;
	  
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

}
