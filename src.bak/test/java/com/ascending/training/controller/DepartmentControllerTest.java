/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 06/2019
 */

package com.ascending.training.controller;

import com.ascending.training.init.AppInitializer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= AppInitializer.class)
@AutoConfigureMockMvc
public class DepartmentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Logger logger;
    private static String token;

    @Before
    public void init() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/auth")
                                           .content("{" +
                                                    " \"name\": \"dwang\"," +
                                                    " \"password\": \"123456789\"," +
                                                    " \"email\": \"dwang@ascending.com\"" +
                                                    "}")
                                           .contentType(MediaType.APPLICATION_JSON))
                                           .andDo(MockMvcResultHandlers.print()).andReturn();

        token = result.getResponse().getContentAsString().replaceAll("Authorization:", "");
        logger.info("Token: " + token);
    }

    @Test
    public void getAllDepartments() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/departments").header("Authorization", token))
               .andDo(MockMvcResultHandlers.print())
               //.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
               .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
