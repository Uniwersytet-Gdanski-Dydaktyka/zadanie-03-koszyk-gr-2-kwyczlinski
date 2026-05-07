package io.github.kwyczlinski;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/* Overview of the tested functionalities

 - basketValue() on empty basket returns 0.0
 - basketValue() sums discountPrice, not price
 - addProduct(null) does not throw, basket unchanged
 - getProducts() returns defensive copy — mutating it does not affect basket
 - getProducts() returns products sorted price descending, then name ascending
 - default sort: price descending, then name ascending for equal prices
 - setSortComparator(null) does not throw, previous comparator unchanged
 - setSortComparator() changes sort order correctly
*/

public class ShoppingBasketBasicsTest {
    ShoppingBasket basket;

    @BeforeEach
    public void createBasket() {
        basket = new ShoppingBasket();
    }

    @Test
    public void shouldInitEmpty() {
        assertTrue(basket.getProducts().isEmpty());
    }

    @Test
    public void shouldHaveCorrectValueWhenEmpty() {
        assertEquals(0.0, basket.getTotal());
    }

    @Test
    public void shouldSumDiscountPrice() {
        basket.addProduct(new Product("1", "mug", 15.0, 3.0));
        basket.addProduct(new Product("2", "mouse", 25.0, 4.0));

        assertEquals(7.0, basket.getTotal());
    }

    @Test
    public void shouldHandleAddingNull() {
        assertDoesNotThrow(() -> basket.addProduct(null));
        assertTrue(basket.getProducts().isEmpty());
    }

    @Test
    public void shouldReturnImmutableProductList() {
        basket.addProduct(new Product("1", "mug", 15.0, 3.0));

        List<Product> products = basket.getProducts();

        assertThrows(UnsupportedOperationException.class, () -> {
            products.add(new Product("2", "mouse", 25.0));
        });
    }

    @Test 
    public void shouldDefaultSortCorrectlyDiscountedPriceDescendingNameAscending() {
        basket.addProduct(new Product("1", "mug", 15.0)); // 2
        basket.addProduct(new Product("2", "mouse", 25.0, 15.0)); // 1
        basket.addProduct(new Product("3", "socks", 20.0, 10.0)); // 3

        List<Product> result = basket.getProducts();

        assertEquals(List.of("2", "3", "1"), result.stream().map(Product::getCode).toList());
    }

    @Test
    public void shouldHandleComparatorNull() {
        assertDoesNotThrow(() -> basket.setSortComparator(null));
        assertNotNull(basket.getSortComparator());
    }

    @Test
    public void shouldChangeSortComparator() {
        basket.addProduct(new Product("1", "mug", 35.0)); // 3
        basket.addProduct(new Product("2", "mouse", 25.0, 15.0)); // 2
        basket.addProduct(new Product("3", "socks", 20.0, 10.0)); // 1

        basket.setSortComparator(Comparator.comparingDouble(Product::getDiscountPrice));
        List<Product> result = basket.getProducts();

        assertEquals(List.of("1", "2", "3"), result.stream().map(Product::getCode).toList());
    }
}