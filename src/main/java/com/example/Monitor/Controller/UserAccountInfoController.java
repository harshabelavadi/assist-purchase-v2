package com.example.Monitor.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Monitor.Exception.ResourceNotFoundException;
import com.example.Monitor.Model.UserAccountInfo;
import com.example.Monitor.Service.UserAccountInfoService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/")
public class UserAccountInfoController {
	@Autowired
	private UserAccountInfoService userService;
	
	// get all users
	@GetMapping("users")
	public List<UserAccountInfo> getAll() {
		return this.userService.getAllObjects();
	}
	
	// get user details by id
	@GetMapping("users/{id}")
	public ResponseEntity<UserAccountInfo> getObjectById(@PathVariable(value = "id") int id) throws ResourceNotFoundException{
		return ResponseEntity.ok().body(this.userService.getObjectById(id));
	}

	// save user record
	@PostMapping("users")
	public UserAccountInfo create(@RequestBody UserAccountInfo user) {
		return this.userService.saveObject(user);
	}
	
	// update user record
	@PutMapping("users/{id}")
	public ResponseEntity<UserAccountInfo> update(@PathVariable(value = "id") int id, 
			@Valid @RequestBody UserAccountInfo userRecord) throws ResourceNotFoundException {
		UserAccountInfo user = this.userService.getObjectById(id);
		user.setPassword(userRecord.getPassword());
		user.setAdmin(userRecord.isAdmin());
		user.setUname(userRecord.getUname());
		return ResponseEntity.ok(this.userService.saveObject(user));
	}
	
	// delete user record
	@DeleteMapping("users/{id}")
	public String delete(@PathVariable(value = "id") int id) throws ResourceNotFoundException {
		this.userService.deleteObject(this.userService.getObjectById(id));
		return "Record with id :: "+id+" is deleted";
	}
}
