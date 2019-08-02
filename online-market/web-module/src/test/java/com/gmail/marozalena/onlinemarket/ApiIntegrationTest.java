package com.gmail.marozalena.onlinemarket;

import com.gmail.marozalena.onlinemarket.service.UserService;
import com.gmail.marozalena.onlinemarket.service.model.UserDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiIntegrationTest {

    @Autowired
    private WebApplicationContext context;
    @Mock
    private UserService userService;
    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
        when(userService.loadUserByEmail(any())).thenReturn(new UserDTO());
    }

    @Test
    public void shouldSaveUser() throws Exception {
        mvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"email\": \"test@gmail.com\", " +
                        "\"password\": \"test\", " +
                        "\"role\": {" +
                        "\"role\": \"Customer User\"" +
                        "}}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldSaveArticle() throws Exception {
        mvc.perform(post("/api/articles")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"topic\": \"Test\", " +
                        "\"picture\": \"test.jpg\", " +
                        "\"date\": \"2019-06-02\", " +
                        "\"body\": \"Attention! Something happened!\"" +
                        "}"))
                .andExpect(status().isCreated());
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
    public void shouldAddItem() throws Exception {
        mvc.perform(post("/api/items")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"name\": \"Test\", " +
                        "\"price\": \"45.13\", " +
                        "\"description\": \"Description\" " +
                        "}"))
                .andExpect(status().isCreated());
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
