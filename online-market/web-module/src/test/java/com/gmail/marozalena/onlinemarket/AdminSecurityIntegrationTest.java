package com.gmail.marozalena.onlinemarket;

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
    private static final String adminAuthority = "Administrator";

    @Before
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(authorities = {adminAuthority})
    @Test
    public void shouldSucceedWith200ForUsersPage() throws Exception {
        mvc.perform(get("/private/users/1"))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = {adminAuthority})
    @Test
    public void shouldSucceedWith302ForUsersPage() throws Exception {
        mvc.perform(get("/private/users"))
                .andExpect(status().is(302));
    }

    @WithMockUser(authorities = {adminAuthority})
    @Test
    public void shouldSucceedWith200ForAddUserPage() throws Exception {
        mvc.perform(get("/private/users/add"))
                .andExpect(status().isOk());
    }

}
