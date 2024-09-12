package com.test.qikserve.qikserve.application;

import com.test.qikserve.qikserve.application.dto.CartRecord;
import com.test.qikserve.qikserve.application.service.CartProductService;
import com.test.qikserve.qikserve.application.web.CartController;
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


@WebMvcTest(CartController.class)
public class CartControllerTest {

    @MockBean
    private CartProductService cartProductService;
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new CartController(cartProductService)).build();
    }


    @Test
    void shouldReturn200WhenGetRequestCartByIdIsMade() throws Exception {
        mockMvc.perform(get("/api/v1/carts/6c5dffb3-0a0d-486a-b620-ce85d950f5a4"))
                .andExpect(status().isOk());

        Mockito.verify(cartProductService).getCartById(Mockito.any());
    }

    @Test
    void shouldReturn201WhenPostNewCart() throws Exception {

        Mockito.when(cartProductService.addProductInCart(Mockito.any())).thenReturn(new CartRecord(UUID.randomUUID(),
                null, null, null));

        mockMvc.perform(post("/api/v1/carts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getNewCartJson()))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Mockito.verify(cartProductService).addProductInCart(Mockito.any());
    }

    @Test
    void shouldReturn204WhenPutCart() throws Exception {

        mockMvc.perform(put("/api/v1/carts/6c5dffb3-0a0d-486a-b620-ce85d950f5a4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getNewCartJson()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(cartProductService).addProductsInCart(Mockito.any(), Mockito.any());
    }

    @Test
    void shouldReturn204WhenDeleteProductFromCart() throws Exception {

        mockMvc.perform(delete("/api/v1/carts/6c5dffb3-0a0d-486a-b620-ce85d950f5a4/products/6c5dffb3-0a0d-486a-b620-ce85d950f5a4")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(cartProductService).removeProductFromCart(Mockito.any(), Mockito.any());
    }


    String getNewCartJson() {
        return """
                {
                     "items": [{
                         "productId": "6c5dffb3-0a0d-486a-b620-ce85d950f5a4",
                         "quantity": 4
                     }]
                 }
                                
                """;
    }

}
