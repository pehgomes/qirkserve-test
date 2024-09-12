package com.test.qikserve.qikserve.application;

import com.test.qikserve.qikserve.application.dto.ProductRecord;
import com.test.qikserve.qikserve.application.service.ProductService;
import com.test.qikserve.qikserve.application.web.ProductController;
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


@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @MockBean
    private ProductService productService;
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ProductController(productService)).build();
    }

    @Test
    void shouldReturn200WhenGetRequestProductsIsMade() throws Exception {
        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk());

        Mockito.verify(productService).getProducts(Mockito.any());
    }

    @Test
    void shouldReturn200WhenGetRequestProductsByIdIsMade() throws Exception {
        mockMvc.perform(get("/api/v1/products/6c5dffb3-0a0d-486a-b620-ce85d950f5a4"))
                .andExpect(status().isOk());

        Mockito.verify(productService).getProductById(Mockito.any());
    }

    @Test
    void shouldReturn201WhenPostNewProduct() throws Exception {

        Mockito.when(productService.create(Mockito.any())).thenReturn(new ProductRecord(UUID.randomUUID().toString(),
                null, null, null));

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getNewProductJson()))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        Mockito.verify(productService).create(Mockito.any());
    }

    @Test
    void shouldReturn204WhenPatchProduct() throws Exception {

        mockMvc.perform(patch("/api/v1/products/6c5dffb3-0a0d-486a-b620-ce85d950f5a4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getNewProductJson()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(productService).update(Mockito.any(), Mockito.any());
    }


    String getNewProductJson() {
        return """
                {
                  "id": "12345",
                  "name": "Sample Product",
                  "price": 29.99,
                  "promotions": [
                    {
                      "id": "promo1",
                      "type": "BUY_X_GET_Y_FREE",
                      "required_qty": 2,
                      "free_qty": 1
                    }
                  ]
                }
                                
                """;
    }

}
