package com.example.Monitor.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Monitor.Model.UserAccountInfo;

@Repository
public interface UserAccountInfoRepository extends JpaRepository<UserAccountInfo, Integer>{

}
