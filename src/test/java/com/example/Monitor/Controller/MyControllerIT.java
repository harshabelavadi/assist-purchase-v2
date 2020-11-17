package com.example.Monitor.Controller;

import com.example.Monitor.MapToJSON;
import com.example.Monitor.MonitorApplication;
import com.example.Monitor.Model.Product;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MonitorApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MyControllerIT {

	private MockMvc mockMvc;
	
	@Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void test1_createProduct() throws Exception {
        String uri = "/products/add";
        Product product = new Product();
        product.setPname("IntelliVue MX400");
        product.setSize(9);
        product.setCategory("intellivue");
        product.setTouchscreen(true);
        product.setTransportMonitor(true);

        String inputJson = MapToJSON.asJsonString(product);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void test2_updateProduct() throws Exception {
        String uri = "/products/update/12";
    	
        Product product = new Product();
        product.setPname("IntelliVue MX40");
        product.setSize(12);
        product.setCategory("effica");
        product.setTouchscreen(true);
        product.setTransportMonitor(true);
        product.setImgname("someimgname");
        product.setDescription("desc");

        String inputJson = MapToJSON.asJsonString(product); 
        
        mockMvc.perform(MockMvcRequestBuilders.put(uri)
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(inputJson)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.pname").exists())
				.andExpect(jsonPath("$.touchscreen").exists())
				.andExpect(jsonPath("$.size").exists())
				.andExpect(jsonPath("$.category").exists())
				.andExpect(jsonPath("$.transportMonitor").exists())
				.andExpect(jsonPath("$.description").exists())
				.andExpect(jsonPath("$.imgname").exists())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.pname").value("IntelliVue MX40"))
				.andExpect(jsonPath("$.touchscreen").value(true))
				.andExpect(jsonPath("$.size").value(12))
				.andExpect(jsonPath("$.category").value("effica"))
				.andExpect(jsonPath("$.transportMonitor").value(true))
				.andExpect(jsonPath("$.description").value("desc"))
				.andExpect(jsonPath("$.imgname").value("someimgname"));
    }
    
    @Test
    public void test3_getAllProducts() throws Exception{
        mockMvc.perform(get("/products/all").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(12)));
    }

    @Test
    public void test4_deleteProduct() throws Exception {
        String uri = "/products/delete/12";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }
    
    @Test
    public void test5_updateProductNotFound() throws Exception {
    	mockMvc.perform(MockMvcRequestBuilders.put("/products/update/200").accept(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}