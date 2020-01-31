package app.repository.imp;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import app.models.Rooms;
import app.models.User;
import app.repository.RestFullRepository;

import org.springframework.data.mongodb.core.query.Criteria;

@Repository
public class RoomsRepository extends RestFullRepository{
	
	public RoomsRepository(MongoOperations mongoOperations) {
		super(mongoOperations);
		
		this.objectClass = Rooms.class;		
		this.keys.add("name");
		this.keys.add("city");
		this.keys.add("description");
		this.keys.add("dragonpassId");
		this.keys.add("picture");
	}
	
	
	public Optional<List<Object>> findAllBySelectedRooms(String[] selectedRooms) {
		// TODO Auto-generated method stub
		
		Query searchQuery = new Query(Criteria.where("id").in(selectedRooms));		
		
		List<Object> dataSet = null;
		
		System.out.println("check info --------------------");	
		
		dataSet = (List<Object>) mongoOperations.find(searchQuery, objectClass);			

        Optional<List<Object>> optionaldataSet = Optional.ofNullable(dataSet);

        return optionaldataSet;
		
	}

}
