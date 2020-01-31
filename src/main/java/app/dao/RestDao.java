package app.dao;

import java.util.List;
import java.util.Optional;


import app.repository.RestFullRepository;

public class RestDao {
	
	protected RestFullRepository restFullRepository;
	
	public RestDao(RestFullRepository restFullRepository) {
		this.restFullRepository = restFullRepository; 
	}

	public Object save(Object object) {
		return this.restFullRepository.save(object);
	}
	
	public void update(Object object, String id) {
		this.restFullRepository.update(object,id);
	}
	
	public void delete(String id) {
		this.restFullRepository.delete(id);
	}
	
	public Object findById(String id){
		
		Optional<Object>  object = this.restFullRepository.findOne(id);
		
		if(object.isPresent())
		{
			return object;
		}
		else { return null; }
		
	}
	
	public List<Object> findAll(){
		
		Optional<List<Object>> dataSet = this.restFullRepository.findAll();
		
		return dataSet.get();
	}
	
}
