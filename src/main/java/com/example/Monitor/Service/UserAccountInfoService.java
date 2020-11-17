package com.example.Monitor.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Monitor.Exception.ResourceNotFoundException;
import com.example.Monitor.Model.UserAccountInfo;
import com.example.Monitor.Repository.UserAccountInfoRepository;

@Component
@org.springframework.stereotype.Service
public class UserAccountInfoService implements IService<UserAccountInfo>{

	private UserAccountInfoRepository userRepository;
	
	@Autowired
    public void setdao(UserAccountInfoRepository userRepository)
    {
        this.userRepository = userRepository;
    }
	
	 public Iterable<UserAccountInfo> save(List<UserAccountInfo> users) {
	        return userRepository.saveAll(users);
	 }
	
	@Override
	public List<UserAccountInfo> getAllObjects() {
		return userRepository.findAll();
	}

	@Override
	public UserAccountInfo getObjectById(int id) throws ResourceNotFoundException {
		UserAccountInfo user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User record not found for this id ::" + id));
		return user;
	}

	@Override
	public UserAccountInfo saveObject(UserAccountInfo user) {
		return userRepository.save(user);
	}

	@Override
	public void deleteObject(UserAccountInfo user) {
		userRepository.delete(user);
	}

}
