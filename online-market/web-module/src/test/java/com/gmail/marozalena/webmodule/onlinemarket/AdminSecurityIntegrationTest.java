package com.gmail.marozalena.webmodule.onlinemarket;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdminSecurityIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    private void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(roles = {"Administrator"})
    @Test
    public void shouldSucceedWith200ForUsersPage() throws Exception {
        mvc.perform(get("/private/users"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = {"Administrator"})
    @Test
    public void shouldSucceedWith200ForAddUserPage() throws Exception {
        mvc.perform(get("/private/add/user"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = {"Administrator"})
    @Test
    public void shouldSucceedWith200ForReviewsPage() throws Exception {
        mvc.perform(get("/private/reviews"))
                .andExpect(status().isOk());
    }
}
