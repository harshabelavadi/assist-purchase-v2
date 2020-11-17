package com.example.Monitor.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Monitor.Exception.ResourceNotFoundException;
import com.example.Monitor.Model.SalesData;
import com.example.Monitor.Repository.SalesDataRepository;

@Component
@org.springframework.stereotype.Service
public class SalesDataService implements IService<SalesData> {

	private SalesDataRepository salesdataRepository;
		
	@Autowired
    public void setdao(SalesDataRepository salesdataRepository)
    {
        this.salesdataRepository = salesdataRepository;
    }
	
	 public Iterable<SalesData> save(List<SalesData> sales) {
	        return salesdataRepository.saveAll(sales);
	 }
	
	@Override
	public List<SalesData> getAllObjects() {
		return salesdataRepository.findAll();
	}

	@Override
	public SalesData getObjectById(int id) throws ResourceNotFoundException {
		SalesData salesdata = salesdataRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sales record not found for this id ::" + id));
		return salesdata;
	}

	@Override
	public SalesData saveObject(SalesData salesdata) {
		return salesdataRepository.save(salesdata);
	}

	@Override
	public void deleteObject(SalesData salesdata) {
		salesdataRepository.delete(salesdata);
	}
}
