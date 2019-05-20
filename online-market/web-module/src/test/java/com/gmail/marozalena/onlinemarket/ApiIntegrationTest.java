package com.gmail.marozalena.onlinemarket;

import com.gmail.marozalena.onlinemarket.service.model.ProfileDTO;
import com.gmail.marozalena.onlinemarket.service.model.RoleDTO;
import com.gmail.marozalena.onlinemarket.service.model.UserDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ApiIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void shouldSaveUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@gmail.com");
        userDTO.setPassword("test");
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(5L);
        userDTO.setProfile(profileDTO);
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(2L);
        userDTO.setRole(roleDTO);
        restTemplate.withBasicAuth("secure@secure.com", "secureapi");
        ResponseEntity responseEntity = restTemplate
                .withBasicAuth("secure@secure.com", "secureapi")
                .postForEntity("http://localhost:8080/api/users", userDTO, ResponseEntity.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void shouldSucceedWith200ForArticlesAPI() throws Exception {
        mvc.perform(get("/api/articles"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldSucceedWith200ForArticleAPI() throws Exception {
        mvc.perform(get("/api/articles/1"))
                .andExpect(status().isOk());
    }
}
