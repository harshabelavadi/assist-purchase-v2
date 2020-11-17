package com.example.Monitor.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Monitor.Exception.ResourceNotFoundException;
import com.example.Monitor.Model.Staff;
import com.example.Monitor.Repository.StaffRepository;

@Component
@org.springframework.stereotype.Service
public class StaffService implements IService<Staff>{
	
	private StaffRepository staffrepository;
	
	@Autowired
    public void setdao(StaffRepository staffdao)
    {
        this.staffrepository = staffdao;
    }
	
	 public Iterable<Staff> save(List<Staff> staff) {
	        return staffrepository.saveAll(staff);
	 }

	@Override
	public List<Staff> getAllObjects() {
		return staffrepository.findAll();
	}

	@Override
	public Staff getObjectById(int id) throws ResourceNotFoundException {
		Staff staff = staffrepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Staff record not found for this id ::" + id));
		return staff;
	}

	@Override
	public Staff saveObject(Staff staff) {
		return staffrepository.save(staff);
	}

	@Override
	public void deleteObject(Staff staff) {
		staffrepository.delete(staff);
	}
	 
}
