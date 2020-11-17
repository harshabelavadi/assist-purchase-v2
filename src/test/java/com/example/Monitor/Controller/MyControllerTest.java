package com.example.Monitor.Controller;

import com.example.Monitor.MapToJSON;
import com.example.Monitor.MonitorApplication;
import com.example.Monitor.Model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MonitorApplication.class)
@SpringBootTest
public class MyControllerTest {

    private MockMvc mockMvc;
    
    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup(){
    	this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    @Test
    public void getAllProducts() throws Exception {

        mockMvc.perform(get("/products/all").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(11)));
    }

    @Test
    public void getProductsById() throws Exception {

        mockMvc.perform((get("/products/{pid}",10)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pid").value(10))
                .andExpect(jsonPath("$.pname").value("Efficia CM Series"))
                .andExpect(jsonPath("$.touchscreen").value(true))
                .andExpect(jsonPath("$.size").value(15))
                .andExpect(jsonPath("$.category").value("effica"))
                .andExpect(jsonPath("$.transportMonitor").value(true));

    }
    
    @Test
    public void getProductsAccParameters() throws Exception {
    	mockMvc.perform((get("/products?touchscreen=true&size=15&category=intellivue&transportMonitor=true")).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
    }
    

    @Test
    public void productAdd() throws Exception {
        Product prod1=new Product();
        prod1.setPname("IntelliVue X3");
        prod1.setTouchscreen(true);
        prod1.setSize(15);
        prod1.setCategory("nighttime Radiant");
        prod1.setTransportMonitor(false);

        mockMvc.perform((post("/products/add")).content(MapToJSON.asJsonString(prod1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pid").value(12))
                .andExpect(jsonPath("$.pname").value("IntelliVue X3"))
                .andExpect(jsonPath("$.touchscreen").value(true))
                .andExpect(jsonPath("$.size").value(15))
                .andExpect(jsonPath("$.category").value("nighttime Radiant"))
                .andExpect(jsonPath("$.transportMonitor").value(false));

    }

    @Test
    public void updateProduct() throws Exception {


        Product prod1=new Product();
        prod1.setPid(1);
        prod1.setPname("IntelliVue X3");
        prod1.setTouchscreen(true);
        prod1.setSize(15);
        prod1.setCategory("nighttime Radiant");
        prod1.setTransportMonitor(false);


        mockMvc.perform((put("/products/update/{pid}",1))
                .content(MapToJSON.asJsonString(prod1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pid").value(1))
                .andExpect(jsonPath("$.pname").value("IntelliVue X3"))
                .andExpect(jsonPath("$.touchscreen").value(true))
                .andExpect(jsonPath("$.size").value(15))
                .andExpect(jsonPath("$.category").value("nighttime Radiant"))
                .andExpect(jsonPath("$.transportMonitor").value(false));
    }

    @Test
    public void DeleteProduct() throws Exception {

        Product prod1=new Product();
        prod1.setPid(10);
        prod1.setPname("IntelliVue X3");
        prod1.setTouchscreen(true);
        prod1.setSize(15);
        prod1.setCategory("nighttime Radiant");
        prod1.setTransportMonitor(false);

        mockMvc.perform((delete("/products/delete/{pid}",10)).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

}

