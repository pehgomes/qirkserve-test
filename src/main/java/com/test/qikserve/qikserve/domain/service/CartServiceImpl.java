package com.test.qikserve.qikserve.domain.service;

import com.test.qikserve.qikserve.application.dto.CartRecord;
import com.test.qikserve.qikserve.application.service.CartProductService;
import com.test.qikserve.qikserve.domain.mapper.CartMapper;
import com.test.qikserve.qikserve.domain.model.Cart;
import com.test.qikserve.qikserve.domain.model.CartProduct;
import com.test.qikserve.qikserve.domain.repository.CartProductRepository;
import com.test.qikserve.qikserve.domain.repository.CartRepository;
import com.test.qikserve.qikserve.infra.exception.NotFoundException;
import com.test.qikserve.qikserve.infra.jpa.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartServiceImpl implements CartProductService {

    private final CartProductRepository cartProductRepository;
    private final ProductJpaRepository productRepository;
    private final CartRepository cartRepository;

    private final CartMapper mapper = Mappers.getMapper(CartMapper.class);

    @Override
    public CartRecord addProductInCart(CartRecord cartRecord) {

        List<CartProduct> items = new ArrayList<>();
        cartRecord.items().forEach(productCart -> {
            var product = productRepository.findById(productCart.productId())
                    .orElseThrow(() -> new NotFoundException("Product not found %s", productCart.productId()));

            var cartProduct = new CartProduct(product, productCart.quantity());
            items.add(cartProduct);
        });

        return mapper.toRecord(cartRepository.save(new Cart(items)));
    }

    @Override
    public CartRecord getCartById(UUID cartId) {
        return cartRepository.findById(cartId)
                .map(mapper::toRecord)
                .orElseThrow(() -> {
                    log.error("Cart not found. {}", cartId);
                    return new NotFoundException("Cart not found. %s", cartId);
                });
    }

    @Override
    public void addProductsInCart(UUID cartId, CartRecord cartRecord) {
        var cart = cartRepository.findById(cartId)
                .orElseThrow(() -> {
                    log.error("Cart not found. {}", cartId);
                    return new NotFoundException("Cart not found. %s", cartId);
                });

        cartRecord.items().forEach(productCart -> {
            var product = productRepository.findById(productCart.productId())
                    .orElseThrow(() -> new NotFoundException("Product not found %s", productCart.productId()));

            cart.getProducts().forEach(cartProduct -> {
                if (cartProduct.getProduct().equals(product)) {
                    cartProduct.incrementQuantity(productCart.quantity());
                    cartProduct.calculateGrossAmount();
                } else {
                    cart.addCartProduct(new CartProduct(product, productCart.quantity()));
                }
            });
        });

        cart.calculatePrices();
        cartRepository.save(cart);
    }

    @Override
    public void removeProductFromCart(UUID cartId, UUID productId) {
        var cartProduct = cartProductRepository.findByIds(cartId, productId)
                .orElseThrow(() -> {
                    log.error("Item not found. {}", productId);
                    return new NotFoundException("Item not found. %s", cartId);
                });

        var cart = cartProduct.getCart();

        if (cartProduct.isUniqueProduct())
            cartProductRepository.delete(cartProduct);

        cartProduct.removeProductItem();
        cartProductRepository.save(cartProduct);

        cart.calculatePrices();
        cartRepository.save(cart);

    }
}
