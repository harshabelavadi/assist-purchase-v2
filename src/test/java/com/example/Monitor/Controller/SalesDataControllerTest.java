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
public class SalesDataControllerTest {
	private MockMvc mockMvc;
	
	@Autowired
    private WebApplicationContext wac;
		
	@Before
	public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void test1_create() throws Exception {
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/sales/")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content("{\"salesid\": 1, \"salescount\":5, \"product\": {\"pid\": 1}}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.salesid").exists())
				.andExpect(jsonPath("$.salescount").exists())
				.andExpect(jsonPath("$.product").exists())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.salesid").value(1))
				.andExpect(jsonPath("$.salescount").value(5))
				.andExpect(jsonPath("$.product.pid").value(1));
		
		mockMvc.perform(MockMvcRequestBuilders.post("/api/sales/")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content("{\"salesid\": 2, \"salescount\":5, \"product\": {\"pid\": 1}}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.salesid").exists())
				.andExpect(jsonPath("$.salescount").exists())
				.andExpect(jsonPath("$.product").exists())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.salesid").value(2))
				.andExpect(jsonPath("$.salescount").value(5))
				.andExpect(jsonPath("$.product.pid").value(1));
	}
	
	@Test
	public void test2_update() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/api/sales/2")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content("{\"salesid\": 2, \"salescount\":10, \"product\": {\"pid\": 1}}")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.salesid").exists())
				.andExpect(jsonPath("$.salescount").exists())
				.andExpect(jsonPath("$.product").exists())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.salesid").value(2))
				.andExpect(jsonPath("$.salescount").value(10))
				.andExpect(jsonPath("$.product.pid").value(1));
	    }	
	
	@Test
	public void test3_getAll() throws Exception {	
		mockMvc.perform(MockMvcRequestBuilders.get("/api/sales/").accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$", hasSize(256)));
	}
	
	
	@Test
	public void test4_getById() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/sales/2").accept(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.salesid").exists())
		.andExpect(jsonPath("$.salescount").exists())
		.andExpect(jsonPath("$.product").exists())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(jsonPath("$.salesid").value(2))
		.andExpect(jsonPath("$.salescount").value(10))
		.andExpect(jsonPath("$.product.pid").value(1));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/sales/300").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	public void test5_delete() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/sales/2").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}

}
