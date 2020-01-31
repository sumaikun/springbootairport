package app.repository;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;


public class RestFullRepository implements commonRepository {
	
	@Autowired
	protected final MongoOperations mongoOperations;
	
	protected List<String> keys = new ArrayList<String>();
	
	protected String className;
	
	protected Class objectClass;
	
	public RestFullRepository(MongoOperations mongoOperations) {

        Assert.notNull(mongoOperations);

        this.mongoOperations = mongoOperations;

    }
	
	@SuppressWarnings("unchecked")
	public Optional<List<Object>> findAll() {
		// TODO Auto-generated method stub
		
		Query searchQuery = new Query();		
		
		List<Object> dataSet = null;
		
		System.out.println("check info --------------------");
		
		System.out.println(keys);
		
		System.out.println(objectClass);
		
		dataSet = (List<Object>) mongoOperations.find(searchQuery, objectClass);			

        Optional<List<Object>> optionaldataSet = Optional.ofNullable(dataSet);

        return optionaldataSet;
		
	}
	
	@SuppressWarnings("unchecked")
	public Optional<Object> findOne(String id) {
		
		Query searchQuery = new Query(Criteria.where("id").is(id));	
		
		Object d = null;
		
		
		d = this.mongoOperations.findOne(searchQuery, objectClass);
		
        Optional<Object> data = Optional.ofNullable(d);

        return data;

    }

	@Override
	public Object save(Object object) {
		
		this.mongoOperations.save(object);
        
		try {
			return findOne(object.getClass().getMethod("getId").invoke(object, null).toString()).get();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public void update(Object object, String id) {
		// TODO Auto-generated method stub
		Update update = this.generateUpdateObject(object, keys);		
		
		
		this.mongoOperations.updateFirst(new Query(Criteria.where("id").is(id)), update, objectClass );
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		this.mongoOperations.findAndRemove(new Query(Criteria.where("id").is(id)), objectClass );
	}

	@Override
	public Update generateUpdateObject(Object object, List<String> keys2) {		
		
		Update update=new Update();		
		
		for(String key : keys2) {
			
			try {
				
				String property_method = "get" + key.substring(0, 1).toUpperCase() + key.substring(1);
					
				//System.out.println(property_method);
				
				Method method = object.getClass().getMethod(property_method,null);				
			
				String data = (String)method.invoke(object,null);
				
				//System.out.println(data);
				
				if(data != null  &&  data.length() > 0)
				{					
					update.set(key,data);
				}
				
				
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}		
		
		return update;
	}

	@Override
	public Update generateUpdateObject(Object object, String[] keys) {
		// TODO Auto-generated method stub
		return null;
	}

}
