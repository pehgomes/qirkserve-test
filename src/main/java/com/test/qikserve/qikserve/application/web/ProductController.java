package com.test.qikserve.qikserve.application.web;

import com.test.qikserve.qikserve.application.dto.ProductFilter;
import com.test.qikserve.qikserve.application.dto.ProductRecord;
import com.test.qikserve.qikserve.application.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Product operations.")
public class ProductController {

    private final ProductService productService;


    @PostMapping
    @Operation(description = "Create a product.")
    public ResponseEntity<Void> updateProduct(@RequestBody ProductRecord record) {
        var product = productService.create(record);

        return ResponseEntity.created(
                        fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(product.id())
                                .toUri())
                .build();
    }

    @GetMapping
    @Operation(description = "Find all products.")
    public Page<ProductRecord> getProducts(@ParameterObject ProductFilter filter) {
        return productService.getProducts(filter);
    }

    @GetMapping("/{productId}")
    @Operation(description = "Find a product by id.")
    public ProductRecord getProductById(@PathVariable UUID productId) {
        return productService.getProductById(productId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{productId}")
    @Operation(description = "Update a product.")
    public void updateProduct(@PathVariable UUID productId, @RequestBody ProductRecord record) {
        productService.update(productId, record);
    }


}
