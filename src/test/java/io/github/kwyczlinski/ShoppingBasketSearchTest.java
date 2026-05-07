package io.github.kwyczlinski;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/* Overview of the tested functionalities

 - getCheapestProduct() on empty basket returns Optional.empty()
 - getPriciestProduct() on empty basket returns Optional.empty()
 - getCheapestProduct() returns correct product regardless of insertion order
 - getNCheapestProducts(0) returns empty list
 - getNCheapestProducts(n) where n > basket.size() returns all products
 - getNPriciestProducts symmetric equivalents of above
 - results reflect discountPrice, not price [indirectly]
*/

public class ShoppingBasketSearchTest {
    ShoppingBasket basket;

    @BeforeEach
    public void createBasket() {
        basket = new ShoppingBasket();
    }

    @Test
    public void shouldCheapestProductReturnOptionalWhenEmpty() {
        Optional<Product> result = basket.getCheapestProduct();

        assertEquals(Optional.empty(), result);
    }

    @Test
    public void shouldPriciestProductReturnOptionalWhenEmpty() {
        Optional<Product> result = basket.getPriciestProduct();
        
        assertEquals(Optional.empty(), result);
    }

    @Test
    public void shouldReturnCheapest() {
        basket.addProduct(new Product("2", "mouse", 25.0, 20.0));
        basket.addProduct(new Product("3", "socks", 20.0, 10.0));
        basket.addProduct(new Product("1", "mug", 15.0));

        Product product = basket.getCheapestProduct().get();

        assertEquals("3", product.getCode());
    }

    @Test
    public void shouldReturnPriciest() {
        basket.addProduct(new Product("2", "mouse", 25.0, 20.0));
        basket.addProduct(new Product("3", "socks", 20.0, 10.0));
        basket.addProduct(new Product("1", "mug", 15.0));

        Product product = basket.getPriciestProduct().get();

        assertEquals("2", product.getCode());
    }

    @Test
    public void shouldReturnEmptyListWhenZeroCheapest() {
        assertTrue(basket.getNCheapestProducts(0).isEmpty());
    }

    @Test
    public void shouldReturnEmptyListWhenZeroPriciest() {
        basket.addProduct(new Product("2", "mouse", 25.0, 20.0));
        basket.addProduct(new Product("3", "socks", 20.0, 10.0));

        assertTrue(basket.getNPriciestProducts(0).isEmpty());
    }

    @Test
    public void shouldReturnAllProductsWhenNCheapestTooBig() {
        basket.addProduct(new Product("2", "mouse", 25.0, 20.0));
        basket.addProduct(new Product("3", "socks", 20.0, 10.0));

        assertEquals(2, basket.getNCheapestProducts(5).size());
    }

    @Test
    public void shouldReturnAllProductsWhenNPriciestTooBig() {
        basket.addProduct(new Product("2", "mouse", 25.0, 20.0));
        basket.addProduct(new Product("3", "socks", 20.0, 10.0));

        assertEquals(2, basket.getNPriciestProducts(3).size());
    }
}