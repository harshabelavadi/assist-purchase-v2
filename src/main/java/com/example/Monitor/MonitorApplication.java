package com.example.Monitor;

import com.example.Monitor.Model.Product;
import com.example.Monitor.Model.Staff;
import com.example.Monitor.Model.SalesData;
import com.example.Monitor.Model.UserAccountInfo;
import com.example.Monitor.Service.SalesDataService;
import com.example.Monitor.Service.ServiceImpl;
import com.example.Monitor.Service.StaffService;
import com.example.Monitor.Service.UserAccountInfoService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class MonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitorApplication.class, args);
	}

    @Bean
    CommandLineRunner runner(ServiceImpl service, StaffService staffService, 
    		UserAccountInfoService userService, SalesDataService salesService) {
        return args -> {
            // read json and write to db
            ObjectMapper mapper = new ObjectMapper();
            
            TypeReference<List<Product>> typeReference = new TypeReference<List<Product>>(){};
            TypeReference<List<Staff>> staffTypeReference = new TypeReference<List<Staff>>(){};
            TypeReference<List<UserAccountInfo>> userTypeReference = new TypeReference<List<UserAccountInfo>>(){};
            TypeReference<List<SalesData>> salesTypeReference = new TypeReference<List<SalesData>>(){};
            
            InputStream inputStream = TypeReference.class.getResourceAsStream("/json/products.json");
            InputStream staffInputStream = TypeReference.class.getResourceAsStream("/json/staff.json"); 
            InputStream userInputStream = TypeReference.class.getResourceAsStream("/json/users.json");
            InputStream salesInputStream = TypeReference.class.getResourceAsStream("/json/sales.json");
            
            try {
            	List<Staff> staff = mapper.readValue(staffInputStream,staffTypeReference);
            	staffService.save(staff); // Staff should be inserted first
            	
            	List<Product> products = mapper.readValue(inputStream,typeReference);
                service.save(products);
                
            	List<UserAccountInfo> users = mapper.readValue(userInputStream,userTypeReference);
            	userService.save(users);
            	
            	List<SalesData> sales = mapper.readValue(salesInputStream, salesTypeReference);
            	salesService.save(sales);
                
            	System.out.println("Products Saved!");
            } catch (IOException e){
                System.out.println("Unable to save Products: " + e.getMessage());
            }
        };
    }

}
