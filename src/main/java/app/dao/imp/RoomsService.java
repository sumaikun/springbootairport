package app.dao.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.config.JwtTokenUtil;
import app.dao.RestDao;
import app.models.User;
import app.repository.imp.RoomsRepository;
import app.repository.imp.UserRepository;

@Service("RoomsServices")
public class RoomsService extends RestDao{
	
	private RoomsRepository roomsRepository;
	
	private UserRepository userRepository;
	
	public RoomsService(RoomsRepository roomsRepository, UserRepository userRepository) {
		super(roomsRepository);
		// TODO Auto-generated constructor stub
		this.roomsRepository = roomsRepository;
		this.userRepository = userRepository;
	}
	
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;	
	
	
	public List<Object> findAll(String token){
		
		String username = jwtTokenUtil.getUsernameFromToken(token);
		
		//System.out.println(username);
		
		User user = this.userRepository.findByUsername(username);
		
		Optional<List<Object>> dataSet = Optional.of(new ArrayList<Object>());
		
		if(user.isAdmin && ( user.getPermissions() == null || user.getPermissions().length == 0 ) )
		{
			System.out.println("first condition");
			dataSet = this.roomsRepository.findAll();
		}
		else {
			System.out.println("second condition");
			dataSet = this.roomsRepository.findAllBySelectedRooms(user.getPermissions());
		}
		
		
		return dataSet.get();
	}
		
	
}
