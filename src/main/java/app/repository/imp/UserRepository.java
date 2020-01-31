package app.repository.imp;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import app.models.User;
import app.repository.commonRepository;
import org.springframework.util.Assert;

@Repository
public class UserRepository implements commonRepository{
	
	@Autowired
	private final MongoOperations mongoOperations;
	
	 @Autowired

    public UserRepository(MongoOperations mongoOperations) {

        Assert.notNull(mongoOperations);

        this.mongoOperations = mongoOperations;

    }


	public Optional<List<User>> findAll() {
		// TODO Auto-generated method stub
		
		Query searchQuery = new Query();
		
		searchQuery.fields().exclude("password");
		
		List<User> users = mongoOperations.find(searchQuery, User.class);		

        Optional<List<User>> optionalUsers = Optional.ofNullable(users);

        return optionalUsers;
		
	}
	
	public Optional<User> findOne(String id) {
		
		Query searchQuery = new Query(Criteria.where("id").is(id));
		
		searchQuery.fields().exclude("password");
		
        User d = this.mongoOperations.findOne(searchQuery, User.class);

        Optional<User> user = Optional.ofNullable(d);

        return user;

    }
	
	public User findByUsername(String username) {
		
		System.out.println("in user repository");
		
		Query searchQuery = new Query(Criteria.where("username").is(username));		
		
        User user = this.mongoOperations.findOne(searchQuery, User.class);
        
		return user;
	}

	@Override
	public Object save(Object object) {
		User user = (User) object;
		this.mongoOperations.save(user);
        return findOne(user.getId()).get();		
	}

	@Override
	public void update(Object object, String id) {
		
		String[] keys = {"username","password","name","secondname","email","IsAdmin","permissions"};
		
		Update update = this.generateUpdateObject(object, keys);		
		
		this.mongoOperations.updateFirst(new Query(Criteria.where("id").is(id)), update, User.class);	
	}

	@Override
	public void delete(String id) {
		this.mongoOperations.findAndRemove(new Query(Criteria.where("id").is(id)), User.class);
		
	}
	
	@Override
	public Update generateUpdateObject(Object object, String[] keys){
		
		User user = (User) object;	
		
		Update update=new Update();		
		
		for(String key : keys) {
			
			try {
				
				String property_method = "get" + key.substring(0, 1).toUpperCase() + key.substring(1);
					
				//System.out.println(property_method);
				
				Method method = user.getClass().getMethod(property_method,null);				
			
				Object data = method.invoke(user,null);
				
				//System.out.println(data);
				
				if(data != null)
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
	public Update generateUpdateObject(Object object, List<String> keys) {
		// TODO Auto-generated method stub
		return null;
	}

}
