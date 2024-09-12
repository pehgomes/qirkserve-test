package com.test.qikserve.qikserve.domain;

import com.test.qikserve.qikserve.application.dto.ProductFilter;
import com.test.qikserve.qikserve.application.dto.ProductRecord;
import com.test.qikserve.qikserve.application.dto.PromotionRecord;
import com.test.qikserve.qikserve.application.service.ProductService;
import com.test.qikserve.qikserve.domain.client.ProductClient;
import com.test.qikserve.qikserve.domain.mapper.ProductMapper;
import com.test.qikserve.qikserve.domain.model.Product;
import com.test.qikserve.qikserve.domain.model.Promotion;
import com.test.qikserve.qikserve.domain.repository.ProductRepository;
import com.test.qikserve.qikserve.domain.service.ProductServiceImpl;
import com.test.qikserve.qikserve.infra.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductClient productClient;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    private ProductService productService;

    @BeforeEach
    void setup() {
        this.productService = productServiceImpl;
    }

    @Test
    void shouldGetProducts() {
        ProductFilter filter = new ProductFilter();

        when(productRepository.findProductByFilter(Mockito.any()))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        productService.getProducts(filter);

        verify(productRepository).findProductByFilter(filter);
    }

    @Test
    void shouldGetProductsById() {
        UUID productId = UUID.randomUUID();
        ProductRecord record = mock(ProductRecord.class);

        when(productRepository.findProductById(productId))
                .thenReturn(Optional.of(mock(Product.class)));

        productService.getProductById(productId);

        verify(productRepository).findProductById(productId);
    }

    @Test
    void shouldGetProductById_NotFound() {
        UUID productId = UUID.randomUUID();

        when(productRepository.findProductById(productId))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.getProductById(productId));

        verify(productRepository).findProductById(productId);
        verify(productMapper, never()).toRecord(any());
    }

    @Test
    void shouldSyncProducts() {
        var products = new ArrayList<ProductRecord>();
        products.add(new ProductRecord(UUID.randomUUID().toString(),
                null, null, null));

        when(productClient.getProducts()).thenReturn(products);

        productService.syncProducts();

        verify(productClient).getProducts();
        verify(productRepository).save(anyList());
    }

    @Test
    void shouldSyncPromotions() {
        var promotions = new ArrayList<PromotionRecord>();
        promotions.add(new PromotionRecord(UUID.randomUUID().toString(),
                null, null, null, null, null));

        var product = new ProductRecord(UUID.randomUUID().toString(),
                null, null, promotions);

        var products = new ArrayList<Product>();
        var productEntity = new Product();
        productEntity.setPromotions(new HashSet<>());
        products.add(productEntity);

        var promotionsEntity = new ArrayList<>();
        promotionsEntity.add(new Promotion());

        when(productRepository.findAllJoinPromotions()).thenReturn(products);
        when(productClient.getProductById(any())).thenReturn(product);

        productService.syncPromotions();

        verify(productRepository).findAllJoinPromotions();
        verify(productClient, times(1)).getProductById(any());
    }

    @Test
    void shouldUpdate() {
        UUID productId = UUID.randomUUID();
        ProductRecord record = mock(ProductRecord.class);
        var product = mock(Product.class);

        when(productRepository.findProductById(productId))
                .thenReturn(Optional.of(product));

        productService.update(productId, record);

        verify(productRepository).findProductById(productId);
        verify(productRepository).save(Mockito.any(Product.class));
    }

    @Test
    void shouldUpdateAndThrowNotFound() {
        UUID productId = UUID.randomUUID();
        ProductRecord record = mock(ProductRecord.class);

        when(productRepository.findProductById(productId))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> productService.update(productId, record));

        verify(productRepository).findProductById(productId);
        verify(productMapper, never()).update(any(), any());
        verify(productRepository, never()).save(Mockito.any(Product.class));
    }

    @Test
    void shouldCreate() {
        ProductRecord record = new ProductRecord(UUID.randomUUID().toString(),
                null, null, null);
        var product = new Product();

        when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

        productService.create(record);

        verify(productRepository).save(Mockito.any(Product.class));
    }
}
