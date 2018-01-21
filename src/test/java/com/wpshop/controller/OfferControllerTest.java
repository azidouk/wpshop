package com.wpshop.controller;

import com.wpshop.Application;
import com.wpshop.model.Offer;
import com.wpshop.model.Store;
import com.wpshop.utils.Common;
import com.wpshop.utils.MessageCatalogue;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static junit.framework.TestCase.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpInputMessage;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class OfferControllerTest {

    private MockMvc mockMvc;
    private HttpMessageConverter converter;

    @Autowired
    private MessageCatalogue catalogue;

    @Autowired
    private Store store;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        this.converter = Arrays.asList(converters).stream()
                .filter(converter -> converter instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);
        assertNotNull("the JSON message converter must not be null",
                this.converter);
    }

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.webApplicationContext).build();
        store.clear();
    }

    @Test
    public void getAllOffersWhenEmpty() throws Exception {
        mockMvc.perform(get("/offer/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty())
                .andExpect(status().isOk());
    }

    @Test
    public void createNew() throws Exception {
        Offer offer = Common.createOffer(1);
        ResultActions response = mockMvc.perform(post("/offer/")
                .content(this.toJson(offer))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        hCheckMatch(response, offer);
    }

    @Test
    public void findNoOffers() throws Exception {
        mockMvc.perform(get("/offer/1000")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findSingleOffer() throws Exception {
        Offer offer = Common.createOffer(1);
        ResultActions response = mockMvc.perform(post("/offer/")
                .content(this.toJson(offer))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        hCheckMatch(response, offer);
        offer = fromJson(response.andReturn().getResponse().getContentAsString());

        response = mockMvc.perform(get("/offer/" + offer.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(offer.getId()));
        hCheckMatch(response, offer);
    }

    @Test
    public void findAllOffers() throws Exception {
        int numOffers = 2;
        Offer[] offers = new Offer[numOffers];
        ResultActions response;
        for (int i = 0; i < numOffers; i++) {
            Offer offer = Common.createOffer(i);
            response = mockMvc.perform(post("/offer/")
                    .content(this.toJson(offer))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());
            hCheckMatch(response, offer);
            offers[i] = fromJson(response.andReturn().getResponse().getContentAsString());
        }

        mockMvc.perform(get("/offer/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(offers[0].getId()))
                .andExpect(jsonPath("$[0].name").value(offers[0].getName()))
                .andExpect(jsonPath("$[0].description").value(offers[0].getDescription()))
                .andExpect(jsonPath("$[0].price.price").value(offers[0].getPrice().getPrice()))
                .andExpect(jsonPath("$[0].price.currency").value(offers[0].getPrice().getCurrency().getName()))
                .andExpect(jsonPath("$[1].id").value(offers[1].getId()))
                .andExpect(jsonPath("$[1].name").value(offers[1].getName()))
                .andExpect(jsonPath("$[1].description").value(offers[1].getDescription()))
                .andExpect(jsonPath("$[1].price.price").value(offers[1].getPrice().getPrice()))
                .andExpect(jsonPath("$[1].price.currency").value(offers[1].getPrice().getCurrency().getName()));
    }

    private MvcResult hCheckMatch(ResultActions response, Offer offer) throws Exception{
        return response.andExpect(jsonPath("$.name").value(offer.getName()))
                .andExpect(jsonPath("$.description").value(offer.getDescription()))
                .andExpect(jsonPath("$.price.price").value(offer.getPrice().getPrice()))
                .andExpect(jsonPath("$.price.currency").value(offer.getPrice().getCurrency().getName()))
                .andReturn();
    }

    private String toJson(Object obj) throws IOException {
        MockHttpOutputMessage mockMessage = new MockHttpOutputMessage();
        this.converter.write(obj, MediaType.APPLICATION_JSON, mockMessage);
        return mockMessage.getBodyAsString();
    }

    private Offer fromJson(String json) throws IOException {
        MockHttpInputMessage mockMessage = new MockHttpInputMessage(json.getBytes());
        return (Offer)this.converter.read(Offer.class, mockMessage);
    }
}