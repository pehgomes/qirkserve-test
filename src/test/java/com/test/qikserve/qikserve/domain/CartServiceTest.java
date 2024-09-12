package com.test.qikserve.qikserve.domain;

import com.test.qikserve.qikserve.application.dto.CartRecord;
import com.test.qikserve.qikserve.application.dto.ProductCartRecord;
import com.test.qikserve.qikserve.domain.model.Cart;
import com.test.qikserve.qikserve.domain.model.Product;
import com.test.qikserve.qikserve.domain.repository.CartProductRepository;
import com.test.qikserve.qikserve.domain.repository.CartRepository;
import com.test.qikserve.qikserve.domain.service.CartServiceImpl;
import com.test.qikserve.qikserve.infra.jpa.ProductJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartProductRepository cartProductRepository;
    @Mock
    private ProductJpaRepository productRepository;
    @Mock
    private CartRepository cartRepository;
    @InjectMocks
    private CartServiceImpl cartService;


    @Test
    void shouldAddProductInCart() {
        ProductCartRecord productCartRecord = new ProductCartRecord(UUID.randomUUID(), null, null, 2L, null, null);
        List<ProductCartRecord> prods = new ArrayList<ProductCartRecord>();
        prods.add(productCartRecord);
        CartRecord cartRecord = new CartRecord(UUID.randomUUID(), prods, null, null);
        var product = new Product();
        product.setPromotions(new HashSet<>());
        product.setPrice(BigDecimal.TEN);
        when(productRepository.findById(Mockito.any())).thenReturn(Optional.of(product));

        cartService.addProductInCart(cartRecord);

        Mockito.verify(productRepository).findById(any());
        Mockito.verify(cartRepository).save(any(Cart.class));
    }
}
