package com.example.Monitor.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Monitor.Model.SalesData;

@Repository
public interface SalesDataRepository extends JpaRepository<SalesData, Integer>{

}
