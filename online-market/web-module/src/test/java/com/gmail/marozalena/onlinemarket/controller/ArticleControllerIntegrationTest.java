package com.gmail.marozalena.onlinemarket.controller;

import com.gargoylesoftware.htmlunit.WebClient;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;

import static com.gmail.marozalena.onlinemarket.web.constant.RoleConstants.CUSTOMER_USER;
import static com.gmail.marozalena.onlinemarket.web.constant.RoleConstants.SALE_USER;
import static com.gmail.marozalena.onlinemarket.web.constant.UrlConstants.URL_TO_ARTICLES_PAGE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticleControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    private WebClient webClient;

    @Before
    public void init() {
        webClient = MockMvcWebClientBuilder.mockMvcSetup(mvc)
                .useMockMvcForHosts("localhost:8080/")
                .build();
    }

    @WithMockUser(authorities = {CUSTOMER_USER})
    @Test
    public void requestForCustomerIsProcessedWithArticlesList() throws Exception {
        this.mvc.perform(get(URL_TO_ARTICLES_PAGE).accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(CoreMatchers.containsString("Articles")));
    }

    @WithMockUser(authorities = {SALE_USER})
    @Test
    public void requestForSaleUserIsProcessedWithArticlesList() throws Exception {
        this.mvc.perform(get(URL_TO_ARTICLES_PAGE).accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(CoreMatchers.containsString("Articles")));
    }

    @WithMockUser(authorities = {CUSTOMER_USER})
    @Test
    public void requestForCustomerIsProcessedWithArticle() throws Exception {
        this.mvc.perform(get("/articles/1").accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(CoreMatchers.containsString("Topic")));
    }

    @WithMockUser(authorities = {SALE_USER})
    @Test
    public void requestForSaleUserIsProcessedWithArticle() throws Exception {
        this.mvc.perform(get("/articles/1").accept(MediaType.parseMediaType("text/html;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string(CoreMatchers.containsString("Topic")));
    }
}
