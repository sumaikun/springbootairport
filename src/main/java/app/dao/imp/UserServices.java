package app.dao.imp;

import java.util.List;
import java.util.Optional;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.models.User;
import app.repository.imp.UserRepository;

@Service("userService")

@Transactional
public class UserServices {
	
	private UserRepository userRepository;

	//@Autowired only have sense when there interface and implement methods

    public UserServices(UserRepository userRepository){

        this.userRepository = userRepository;

    }
	
	public User findByUserId(String userId) {

        Optional<User> user = userRepository.findOne(userId);

        if (user.isPresent()) {            

            return user.get();

        }else {
        	return null;
        }            

    }
	
	public List<User> findAll() {

		Optional<List<User>> user = userRepository.findAll();

		 return user.get();

	}
	
	public User saveUser(User user) {

        return (User) userRepository.save(user);

    }


    public void updateUser(User user, String userId) {

        userRepository.update(user,userId);

    }

    public void deleteUser(String userId) {

        userRepository.delete(userId);

    }


	
	

}
