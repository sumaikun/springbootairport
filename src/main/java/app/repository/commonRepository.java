package app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.query.Update;

public interface commonRepository {


	  public Object save(Object object);


	  public void update(Object object, String id);


	  public void delete(String id);
	  
	  
	  public Update generateUpdateObject(Object object, String[] keys);
	  
	  public Update generateUpdateObject(Object object, List<String> keys);

	 
}
