package app.repository.imp;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;
import app.repository.RestFullRepository;
import app.models.Country;

@Repository
public class CountryRepository extends RestFullRepository {

	public CountryRepository(MongoOperations mongoOperations) {
		super(mongoOperations);
		// TODO Auto-generated constructor stub
		this.objectClass = Country.class;		
		this.keys.add("name");
		System.out.println("I am country repository");		
		System.out.println(Country.class);
	}

}
