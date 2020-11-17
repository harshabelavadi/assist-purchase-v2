package com.example.Monitor.Controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.Monitor.MonitorApplication;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MonitorApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserAccountInfoControllerTest {
	private MockMvc mockMvc;
	
	@Autowired
    private WebApplicationContext wac;
		
	@Before
	public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void test1_create() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/users/")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content("{\"uname\": \"admin3\", \"password\": \"password\", \"admin\":true}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.uname").exists())
				.andExpect(jsonPath("$.password").exists())
				.andExpect(jsonPath("$.admin").exists())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.uname").value("admin3"))
				.andExpect(jsonPath("$.password").value("password"))
				.andExpect(jsonPath("$.admin").value(true));
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/users/")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content("{\"uname\": \"user2\", \"password\": \"password\", \"admin\":false}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.uname").exists())
				.andExpect(jsonPath("$.password").exists())
				.andExpect(jsonPath("$.admin").exists())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.uname").value("user2"))
				.andExpect(jsonPath("$.password").value("password"))
				.andExpect(jsonPath("$.admin").value(false));
	}
	
	@Test
	public void test2_update() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/api/users/5")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content("{\"uname\": \"user2_updated\", \"password\": \"password\", \"admin\":true}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.uname").exists())
				.andExpect(jsonPath("$.password").exists())
				.andExpect(jsonPath("$.admin").exists())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.uname").value("user2_updated"))
				.andExpect(jsonPath("$.password").value("password"))
				.andExpect(jsonPath("$.admin").value(true));
	    }	
	
	@Test
	public void test3_getAll() throws Exception {	
		mockMvc.perform(MockMvcRequestBuilders.get("/api/users/").accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$", hasSize(5)));
	}
	
	@Test
	public void test4_getById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/users/5").accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.uname").exists())
		.andExpect(jsonPath("$.password").exists())
		.andExpect(jsonPath("$.admin").exists())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$.uname").value("user2_updated"))
		.andExpect(jsonPath("$.password").value("password"))
		.andExpect(jsonPath("$.admin").value(true));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/users/7").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	public void test5_delete() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/5").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
