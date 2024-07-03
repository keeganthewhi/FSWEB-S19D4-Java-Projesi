package com.workintech.employee.testing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workintech.employee.testing.entity.Employee;
import com.workintech.employee.testing.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    void findByOrder() throws Exception {
        Employee employee1 = new Employee();
        employee1.setFirstName("Ahmet");
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee1);

        when(employeeService.findByOrder()).thenReturn(employeeList);

        mockMvc.perform(get("/employee/byOrder"))
                .andExpect(status().isOk());

        verify(employeeService).findByOrder();
    }

    @Test
    void save() throws Exception {
        Employee employee = new Employee();
        employee.setFirstName("Ahmet");
        employee.setLastName("Yalçın");
        employee.setEmail("ay@test.com");

        when(employeeService.save(employee)).thenReturn(employee);

        mockMvc.perform(post("/employee/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(employee))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Dogancan"));

        verify(employeeService).save(employee);
    }

    public static String asJsonString(Object object){
        try{
            return new ObjectMapper().writeValueAsString(object);
        }catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }
}