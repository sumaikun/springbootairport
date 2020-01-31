package app.models;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "rooms")
public class Rooms implements Serializable {
	
	private static final long serialVersionUID = -7788619177798333712L; 

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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDragonpassId() {
		return dragonpassId;
	}

	public void setDragonpassId(String dragonpassId) {
		this.dragonpassId = dragonpassId;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	@Id
	public String id;
	  
	  @NotNull
	  @Indexed(unique=true)
	  public String name;
	  
	  @NotNull
	  public String city;	  
	  
	  public String description;
	  
	  @NotNull
	  public String dragonpassId;	  
	
	  public String picture;

}
