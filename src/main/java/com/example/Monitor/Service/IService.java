package com.example.Monitor.Service;

import java.util.List;

import com.example.Monitor.Exception.ResourceNotFoundException;

public interface IService<T> {
	public List<T> getAllObjects();
	public T getObjectById(int id) throws ResourceNotFoundException;
	public T saveObject(T object);
	public void deleteObject(T object);
}
