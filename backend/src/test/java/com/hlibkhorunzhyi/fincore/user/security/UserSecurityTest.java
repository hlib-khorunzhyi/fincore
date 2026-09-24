package com.hlibkhorunzhyi.fincore.user.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
//@AutoConfigureMockMvc
//public class UserSecurityTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    @WithMockUser(roles = "ADMIN")
//    void shouldAllowAdminToAccessEndpoint() throws Exception {
//
//        mockMvc.perform(get("/api/v1/users/admin/test"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("You are admin"));
//    }
//
//    @Test
//    @WithMockUser(roles = "USER")
//    void shouldDenyAdminToAccessEndpoint() throws Exception {
//        mockMvc.perform(get("/api/v1/users/admin/test"))
//                .andExpect(status().isForbidden());
//    }
//}
