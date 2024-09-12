package com.test.qikserve.qikserve.application.web;

import com.test.qikserve.qikserve.application.dto.CartRecord;
import com.test.qikserve.qikserve.application.service.CartProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
@Tag(name = "Cart", description = "Cart operations.")
public class CartController {

    private final CartProductService cartProductService;

    @PostMapping
    public ResponseEntity<Void> createCart(@RequestBody CartRecord cartRecord) {
        var cart = cartProductService.addProductInCart(cartRecord);

        return ResponseEntity.created(
                        fromCurrentRequest()
                                .path("/{cartId}")
                                .buildAndExpand(cart.id())
                                .toUri())
                .build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{cartId}")
    public void updateCart(@PathVariable UUID cartId, @RequestBody CartRecord cartRecord) {
        cartProductService.addProductsInCart(cartId, cartRecord);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{cartId}/products/{productId}")
    public void removeProductCart(@PathVariable UUID cartId, @PathVariable UUID productId) {
        cartProductService.removeProductFromCart(cartId, productId);
    }

    @GetMapping("/{cartId}")
    public CartRecord getCart(@PathVariable UUID cartId) {
        return cartProductService.getCartById(cartId);
    }
}
