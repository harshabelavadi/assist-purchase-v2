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
public class StaffControllerTest {
	private MockMvc mockMvc;
	
	@Autowired
    private WebApplicationContext wac;
		
	@Before
	public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void test1_create() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/staff/")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content("{\"staffname\": \"persona\", \"email\": \"persona@gmail.com\", \"mobile\":\"97836724452\"}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.staffid").exists())
				.andExpect(jsonPath("$.staffname").exists())
				.andExpect(jsonPath("$.email").exists())
				.andExpect(jsonPath("$.mobile").exists())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.staffid").value(4))
				.andExpect(jsonPath("$.staffname").value("persona"))
				.andExpect(jsonPath("$.email").value("persona@gmail.com"))
				.andExpect(jsonPath("$.mobile").value("97836724452"));
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/staff/")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content("{\"staffname\": \"personb\", \"email\": \"personb@gmail.com\", \"mobile\":\"96836724452\"}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.staffid").exists())
				.andExpect(jsonPath("$.staffname").exists())
				.andExpect(jsonPath("$.email").exists())
				.andExpect(jsonPath("$.mobile").exists())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.staffid").value(5))
				.andExpect(jsonPath("$.staffname").value("personb"))
				.andExpect(jsonPath("$.email").value("personb@gmail.com"))
				.andExpect(jsonPath("$.mobile").value("96836724452"));
	}
	
	@Test
	public void test2_update() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/api/staff/5")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content("{\"staffname\": \"personc\", \"email\": \"personc@gmail.com\", \"mobile\":\"89757837592\"}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.staffid").exists())
				.andExpect(jsonPath("$.staffname").exists())
				.andExpect(jsonPath("$.email").exists())
				.andExpect(jsonPath("$.mobile").exists())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.staffid").value(5))
				.andExpect(jsonPath("$.staffname").value("personc"))
				.andExpect(jsonPath("$.email").value("personc@gmail.com"))
				.andExpect(jsonPath("$.mobile").value("89757837592"));
	    }	
	
	@Test
	public void test3_getAll() throws Exception {	
		mockMvc.perform(MockMvcRequestBuilders.get("/api/staff/").accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$", hasSize(5)));
	}
	
	@Test
	public void test4_getById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/staff/5").accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.staffid").exists())
		.andExpect(jsonPath("$.staffname").exists())
		.andExpect(jsonPath("$.email").exists())
		.andExpect(jsonPath("$.mobile").exists())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$.staffid").value(5))
		.andExpect(jsonPath("$.staffname").value("personc"))
		.andExpect(jsonPath("$.email").value("personc@gmail.com"))
		.andExpect(jsonPath("$.mobile").value("89757837592"));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/staff/7").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	public void test5_delete() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/staff/5").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
