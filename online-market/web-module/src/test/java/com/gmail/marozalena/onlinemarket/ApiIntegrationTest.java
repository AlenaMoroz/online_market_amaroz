package com.gmail.marozalena.onlinemarket;

import com.gmail.marozalena.onlinemarket.service.model.ArticleDTO;
import com.gmail.marozalena.onlinemarket.service.model.ItemDTO;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    public void shouldSaveUser() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@gmail.com");
        userDTO.setPassword("test");
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRole("Customer User");
        userDTO.setRole(roleDTO);
        ProfileDTO profileDTO = new ProfileDTO();
        userDTO.setProfile(profileDTO);
        restTemplate.withBasicAuth("rest@test.com", "rest");
        ResponseEntity responseEntity = restTemplate
                .withBasicAuth("rest@rest.com", "rest")
                .postForEntity("http://localhost:8080/api/users", userDTO, ResponseEntity.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void shouldSaveArticle() throws Exception {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setTopic("TTTTTTTTTeeeeeeeeeeeeeesssssssst");
        articleDTO.setPicture("test.jpg");
        articleDTO.setDate("2019-06-02");
        articleDTO.setBody("Earlier this month, the regime tested several short-range missiles, launching them from the Hodo peninsula in the east of the country. North Korean state media said Mr Kim personally oversaw a \"strike drill\" testing various missile components.\n" +
                "That test came after Pyongyang said it had tested what it described as a new \"tactical guided weapon\" in April.\n" +
                "Neither violate North Korea's promise not to test long range or nuclear missiles. Yet, they are likely to cause unease in Japan.\n" +
                "Speaking in Tokyo last week, Mr Abe mirrored Mr Bolton's comments, calling North Korea's recent missile launches \"a breach of UN Security Council resolutions and extremely regrettable\".\n");
        restTemplate.withBasicAuth("rest@rest.com", "rest");
        ResponseEntity responseEntity = restTemplate
                .withBasicAuth("rest@rest.com", "rest")
                .postForEntity("http://localhost:8080/api/articles", articleDTO, ResponseEntity.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void shouldSucceedWith200ForArticlesAPI() throws Exception {
        mvc.perform(get("/api/articles"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteArticle() throws Exception {
        mvc.perform(post("/api/articles/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldAddItem() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName("Test");
        itemDTO.setPrice(45.13);
        itemDTO.setDescription("Description");
        restTemplate.withBasicAuth("secure@secure.com", "secureapi");
        ResponseEntity responseEntity = restTemplate
                .withBasicAuth("rest@rest.com", "rest")
                .postForEntity("http://localhost:8080/api/items", itemDTO, ResponseEntity.class);
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void shouldSucceedWith200ForItemsAPI() throws Exception {
        mvc.perform(get("/api/items"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteItem() throws Exception {
        mvc.perform(post("/api/items/1"))
                .andExpect(status().isOk());
    }
}
