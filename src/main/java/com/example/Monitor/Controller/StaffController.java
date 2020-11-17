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
import com.example.Monitor.Model.Staff;
import com.example.Monitor.Service.StaffService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/")
public class StaffController {
	@Autowired
	private StaffService staffService;
	
	// get all staff details
	@GetMapping("staff")
	public List<Staff> getAll() {
		return this.staffService.getAllObjects();
	}
	
	// get staff details by id
	@GetMapping("staff/{id}")
	public ResponseEntity<Staff> getObjectById(@PathVariable(value = "id") int id) throws ResourceNotFoundException{
		return ResponseEntity.ok().body(this.staffService.getObjectById(id));
	}

	// save staff details record
	@PostMapping("staff")
	public Staff create(@RequestBody Staff salesdata) {
		return this.staffService.saveObject(salesdata);
	}
	
	// update staff details record
	@PutMapping("staff/{id}")
	public ResponseEntity<Staff> update(@PathVariable(value = "id") int id, 
			@Valid @RequestBody Staff staffRecord) throws ResourceNotFoundException {
		Staff staff = this.staffService.getObjectById(id);
		staff.setStaffname(staffRecord.getStaffname());		
		staff.setEmail(staffRecord.getEmail());	
		staff.setMobile(staffRecord.getMobile());		
		return ResponseEntity.ok(this.staffService.saveObject(staff));
	}
	
	// delete staff details record
	@DeleteMapping("staff/{id}")
	public String delete(@PathVariable(value = "id") int id) throws ResourceNotFoundException {
		this.staffService.deleteObject(this.staffService.getObjectById(id));
		return "Record with id :: "+id+" is deleted";
	}
}
