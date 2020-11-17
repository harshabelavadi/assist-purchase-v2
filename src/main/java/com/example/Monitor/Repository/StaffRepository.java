package com.example.Monitor.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Monitor.Model.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {

}
