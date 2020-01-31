package app.dao.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.config.JwtTokenUtil;
import app.models.BusinessEvent;
import app.models.Rooms;
import app.models.User;
import app.repository.imp.BusinessEventsRepository;
import app.repository.imp.RoomsRepository;
import app.repository.imp.UserRepository;

@Service("BusinessEventsServices")

@Transactional
public class BusinessEventsServices {
	
	private BusinessEventsRepository businessRepository;
	
	private UserRepository userRepository;
	
	private RoomsRepository roomsRepository;
	
	private HttpServletRequest request;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;	
	
	public BusinessEventsServices(BusinessEventsRepository businessRepository,
			HttpServletRequest request, UserRepository userRepository,
			RoomsRepository roomsRepository){

        this.businessRepository = businessRepository;
        
        this.request = request;
        
        this.userRepository = userRepository;
        
        this.roomsRepository = roomsRepository;

    }
	
	public BusinessEvent saveBusinessEvent(BusinessEvent businessEvents) {

        return (BusinessEvent) businessRepository.save(businessEvents);

    }


    public void updateBusinessEvent(BusinessEvent businessEvents, String userId) {

    	businessRepository.update(businessEvents,userId);

    }
    
    public BusinessEvent findByCard(String cardno) {
    	
    	Optional<BusinessEvent> businessEvent =  businessRepository.findByCard(cardno);

    	if (businessEvent.isPresent()) {            

            return businessEvent.get();

        }else {
        	return null;
        }     

    }
    
    public List<BusinessEvent> findAll() {
    	
    	String token = request.getHeader("Authorization").replace("Bearer ", "");
    	
    	//System.out.println(token);
    	
    	String username = jwtTokenUtil.getUsernameFromToken(token);
    	
    	User user = this.userRepository.findByUsername(username);
    	
    	Optional<List<BusinessEvent>> bss = Optional.of(new ArrayList<BusinessEvent>());
    	
    	if(user.isAdmin && ( user.getPermissions() == null || user.getPermissions().length == 0 ) )
		{
    		bss = businessRepository.findAll();
		}
    	else {
    		
    		Optional<List<Object>> avaliableRooms = this.roomsRepository.findAllBySelectedRooms(user.getPermissions());
    		
    		List<String> dragonPassLounges = new ArrayList<String>();
    		
    		List<Rooms> rooms = (List<Rooms>)(Object) avaliableRooms.get();
    		
    		for(Rooms room : rooms)
    		{
    			dragonPassLounges.add(room.getDragonpassId());
    		}
    		
    		bss = businessRepository.findBySelectedRooms(dragonPassLounges);
    		
    	}
		

		return bss.get();

	}
    
    public List<BusinessEvent> findByDate(String date1, String date2) {

		Optional<List<BusinessEvent>> bss = businessRepository.findByDate(date1,date2);

		return bss.get();

	}
    

}
