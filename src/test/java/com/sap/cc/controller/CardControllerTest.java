package com.sap.cc.controller;

import com.sap.cc.model.CreditCard;
import com.sap.cc.service.CardService;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class CardControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CardService cardService;
    @InjectMocks
    private CardController cardController;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cardController).build();
    }

    @Test
    public void testSuccessAddCreditCard() throws Exception {
        doNothing().when(cardService).processNewCard(any());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "user1");
        jsonObject.put("cardNumber", "1234432156788765");
        jsonObject.put("limit", 10000.00);
        mockMvc.perform(post("/api/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonObject.toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllCards() throws Exception {
        CreditCard card1 = CreditCard.builder().cardNumber("cardNum1").cardLimit(20000.00).name("user1").build();
        CreditCard card2 = CreditCard.builder().cardNumber("cardNum2").cardLimit(10000.00).name("user2").build();
        when(cardService.getAllCards()).thenReturn(Arrays.asList(card1, card2));
        mockMvc.perform(get("/api/getAllCards"))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"cardId\":null,\"name\":\"user1\",\"cardNumber\":\"cardNum1\",\"cardLimit\":20000.0,\"balance\":0.0},{\"cardId\":null,\"name\":\"user2\",\"cardNumber\":\"cardNum2\",\"cardLimit\":10000.0,\"balance\":0.0}]"));
    }
}
