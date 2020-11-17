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
import com.example.Monitor.Model.SalesData;
import com.example.Monitor.Service.SalesDataService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/")
public class SalesDataController {
	
	@Autowired
	private SalesDataService salesdataService;
	
	// get sales
	@GetMapping("sales")
	public List<SalesData> getAll() {
		return this.salesdataService.getAllObjects();
	}
	
	// get sales by id
	@GetMapping("sales/{id}")
	public ResponseEntity<SalesData> getObjectById(@PathVariable(value = "id") int id) throws ResourceNotFoundException{
		return ResponseEntity.ok().body(this.salesdataService.getObjectById(id));
	}

	// save sales record
	@PostMapping("sales")
	public SalesData create(@RequestBody SalesData salesdata) {
		return this.salesdataService.saveObject(salesdata);
	}
	
	// update sales record
	@PutMapping("sales/{id}")
	public ResponseEntity<SalesData> update(@PathVariable(value = "id") int id, 
			@Valid @RequestBody SalesData salesRecord) throws ResourceNotFoundException {
		SalesData sales = this.salesdataService.getObjectById(id);
		sales.setSalescount(salesRecord.getSalescount());		
		return ResponseEntity.ok(this.salesdataService.saveObject(sales));
	}
	
	// delete sales record
	@DeleteMapping("sales/{id}")
	public String delete(@PathVariable(value = "id") int id) throws ResourceNotFoundException {
		this.salesdataService.deleteObject(this.salesdataService.getObjectById(id));
		return "Record with id :: "+id+" is deleted";
	}
}
