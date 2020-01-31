package app.repository.imp;


import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;
import app.repository.RestFullRepository;
import app.models.City;


@Repository
public class CityRepository extends RestFullRepository {

	public CityRepository(MongoOperations mongoOperations) {
		super(mongoOperations);
		// TODO Auto-generated constructor stub
		this.objectClass = City.class;		
		this.keys.add("name");
		this.keys.add("country");
		
	}
	


}
