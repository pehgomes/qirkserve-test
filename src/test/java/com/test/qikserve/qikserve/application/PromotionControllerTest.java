package com.test.qikserve.qikserve.application;

import com.test.qikserve.qikserve.application.dto.PromotionRecord;
import com.test.qikserve.qikserve.application.service.PromotionService;
import com.test.qikserve.qikserve.application.web.PromotionController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PromotionController.class)
public class PromotionControllerTest {

    @MockBean
    private PromotionService promotionService;
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new PromotionController(promotionService)).build();
    }

    @Test
    void shouldReturn200WhenGetRequestProductsIsMade() throws Exception {
        mockMvc.perform(get("/api/v1/promotions"))
                .andExpect(status().isOk());

        Mockito.verify(promotionService).getPromotions(Mockito.any());
    }

    @Test
    void shouldReturn200WhenGetRequestPromotionByIdIsMade() throws Exception {
        mockMvc.perform(get("/api/v1/promotions/6c5dffb3-0a0d-486a-b620-ce85d950f5a4"))
                .andExpect(status().isOk());

        Mockito.verify(promotionService).getById(Mockito.any());
    }

    @Test
    void shouldReturn201WhenPostNewPromotion() throws Exception {

        Mockito.when(promotionService.createPromotion(Mockito.any())).thenReturn(new PromotionRecord(UUID.randomUUID().toString(),
                null, null, null, null, null));

        mockMvc.perform(post("/api/v1/promotions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getNewPromotionJson()))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Mockito.verify(promotionService).createPromotion(Mockito.any());
    }

    @Test
    void shouldReturn204WhenPatchProduct() throws Exception {

        mockMvc.perform(patch("/api/v1/promotions/6c5dffb3-0a0d-486a-b620-ce85d950f5a4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getNewPromotionJson()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(promotionService).updatePromotion(Mockito.any(), Mockito.any());
    }


    String getNewPromotionJson() {
        return """
                {
                   "id": "promo123",
                   "type": "BUY_X_GET_Y_FREE",
                   "required_qty": 3,
                   "free_qty": 1,
                   "price": 50.00,
                   "amount": 10.00
                 }
                 
                                
                """;
    }

}
