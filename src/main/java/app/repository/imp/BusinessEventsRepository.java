package app.repository.imp;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import app.models.BusinessEvent;
import app.models.User;
import app.repository.commonRepository;

@Repository
public class BusinessEventsRepository implements commonRepository{
	
	@Autowired
	private final MongoOperations mongoOperations;
	
	public BusinessEventsRepository(MongoOperations mongoOperations) {

        Assert.notNull(mongoOperations);

        this.mongoOperations = mongoOperations;

    }
	
	public Optional<List<BusinessEvent>> findAll() {
		// TODO Auto-generated method stub
		
		Query searchQuery = new Query();
		
		searchQuery.fields().exclude("password");
		
		List<BusinessEvent> businessEvents = mongoOperations.find(searchQuery, BusinessEvent.class);		

        Optional<List<BusinessEvent>> optionalBss = Optional.ofNullable(businessEvents);

        return optionalBss;
		
	}
	
	public Optional<List<BusinessEvent>> findBySelectedRooms(List<String> dragonPassLounges) {
		// TODO Auto-generated method stub
		
		Query searchQuery = new Query(Criteria.where("lounge").in(dragonPassLounges));	
		
		List<BusinessEvent> businessEvents = mongoOperations.find(searchQuery, BusinessEvent.class);		

        Optional<List<BusinessEvent>> optionalBss = Optional.ofNullable(businessEvents);

        return optionalBss;
		
	}
	
	public Optional<BusinessEvent> findOne(String id) {
		
		Query searchQuery = new Query(Criteria.where("id").is(id));		
		
		BusinessEvent d = this.mongoOperations.findOne(searchQuery, BusinessEvent.class);

        Optional<BusinessEvent> businessEvent = Optional.ofNullable(d);

        return businessEvent;

    }
	
	public Optional<BusinessEvent> findByCard(String cardno) {
		
		Query searchQuery = new Query(Criteria.where("card").is(cardno));		
		
		searchQuery.with(Sort.by(Sort.Direction.DESC, "createdDate"));
		
		BusinessEvent d = this.mongoOperations.findOne(searchQuery, BusinessEvent.class);

        Optional<BusinessEvent> businessEvent = Optional.ofNullable(d);

        return businessEvent;

    }
	
	public Optional<List<BusinessEvent>> findByDate(String date1, String date2) {		
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS\'Z\'");
		dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));

		Date startDate,endDate;	

		String fromDate = date1+"T00:00:00.000Z";
		String toDate = date2+"T24:59:59.000Z";
		
		System.out.println(fromDate);
		
		List<BusinessEvent> businessEvents = null;

		try {
			
			startDate =  Date.from(Instant.parse(fromDate));
			endDate = dateFormatter.parse(toDate);
			
			System.out.println(startDate);
			
			System.out.println(endDate);			

			Query searchQuery = new Query().addCriteria(new Criteria().orOperator(
		          new Criteria().andOperator(Criteria.where("createdDate").gte(startDate),
		          Criteria.where("createdDate").lte(endDate))
	        ));
			
			businessEvents = mongoOperations.find(searchQuery, BusinessEvent.class);
			
			System.out.println(businessEvents);
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Optional<List<BusinessEvent>> optionalBss = Optional.ofNullable(businessEvents);

        return optionalBss;

    }
	
	public Optional<List<BusinessEvent>> findByDateAndSelectedRooms(String date1, String date2,
			String[] allowedLounges) {		
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS\'Z\'");
		dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));

		Date startDate,endDate;	

		String fromDate = date1+"T00:00:00.000Z";
		String toDate = date2+"T24:59:59.000Z";
		
		System.out.println(fromDate);
		
		List<BusinessEvent> businessEvents = null;

		try {
			
			startDate =  Date.from(Instant.parse(fromDate));
			endDate = dateFormatter.parse(toDate);
			
			System.out.println(startDate);
			
			System.out.println(endDate);			

			Query searchQuery = new Query().addCriteria(new Criteria().orOperator(
		          new Criteria().andOperator(Criteria.where("lounge").in(allowedLounges))
	        ));
			
			businessEvents = mongoOperations.find(searchQuery, BusinessEvent.class);
			
			System.out.println(businessEvents);
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Optional<List<BusinessEvent>> optionalBss = Optional.ofNullable(businessEvents);

        return optionalBss;

    }

	@Override
	public Object save(Object object) {
		BusinessEvent businessEvent = (BusinessEvent) object;
		this.mongoOperations.save(businessEvent);
        return findOne(businessEvent.getId()).get();
	}

	@Override
	public void update(Object object, String id) {
		
		String[] keys = {"customer","transaction","card","state","lounge","adultC","childC"};
		
		Update update = this.generateUpdateObject(object, keys);		
		
		this.mongoOperations.updateFirst(new Query(Criteria.where("id").is(id)), update, BusinessEvent.class);		
	
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Update generateUpdateObject(Object object, String[] keys) {

		BusinessEvent dataSource = (BusinessEvent) object;	
		
		Update update=new Update();
		
		for(String key : keys) {
			
			try {
				
				String property_method = "get" + key.substring(0, 1).toUpperCase() + key.substring(1);
					
				//System.out.println(property_method);
				
				Method method = dataSource.getClass().getMethod(property_method,null);				
			
				Object data = method.invoke(dataSource,null);
				
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
