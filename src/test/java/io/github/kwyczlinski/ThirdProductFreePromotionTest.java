package io.github.kwyczlinski;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/* Overview of the tested functionalities

 - fewer than 3 products — no change
 - exactly 3 products — cheapest becomes free
 - 4+ products — only cheapest becomes free, others unchanged
 - two products with identical price — only one becomes free (reference equality)
 - discount applies to discountPrice, not price [indirectly]
 - handles empty list correctly
 - does not throw when null passed as argument
 - handles null in argument list
*/

public class ThirdProductFreePromotionTest {
    private static final Promotion promotion = new ThirdProductFreePromotion();

    @Test
    public void shouldNotApplyWhenLessThen3() {
        List<Product> result = promotion.apply(List.of(new Product("1", "mug", 300.0)));

        double productPrice = result.getFirst().getDiscountPrice();

        assertEquals(300.0, productPrice);
    }

    @Test
    public void shouldApplyCorrectlyWhenExacly3() {
        List<Product> result = promotion.
                                    apply(List.of(
                                        new Product("1", "mug", 35.0),
                                        new Product("2", "mouse", 25.0, 15.0),
                                        new Product("3", "socks", 20.0, 10.0)
                                    ));

        double productsPrices = result.stream().mapToDouble(Product::getDiscountPrice).sum();

        assertEquals(50.0, productsPrices, 0.01);
    }


    @Test
    public void shouldApplyCorrectlyWhenMany() {
        List<Product> result = promotion.
                                    apply(List.of(
                                        new Product("1", "mug", 35.0),
                                        new Product("2", "mouse", 25.0, 15.0),
                                        new Product("3", "socks", 20.0, 10.0),
                                        new Product("4", "flippers", 30.0),
                                        new Product("5", "phone case", 25.0),
                                        new Product("6", "hat", 20.0, 15.0)
                                    ));

        double productsPrices = result.stream().mapToDouble(Product::getDiscountPrice).sum();

        assertEquals(120.0, productsPrices, 0.01);
    }

    @Test
    public void shouldApplyDiscountToOneCheapest() {
        List<Product> result = promotion.
                                    apply(List.of(
                                        new Product("1", "mug", 35.0),
                                        new Product("2", "mouse", 25.0, 15.0),
                                        new Product("3", "socks", 20.0, 10.0),
                                        new Product("4", "flippers", 30.0),
                                        new Product("5", "phone case", 25.0),
                                        new Product("6", "hat", 20.0, 15.0),
                                        new Product("7", "pen", 10.0)
                                    ));

        double productsPrices = result.stream().mapToDouble(Product::getDiscountPrice).sum();

        assertEquals(130.0, productsPrices, 0.01);
    }

    @Test
    public void shouldHandleEmptyList() {
        assertDoesNotThrow(() -> promotion.apply(List.of()));

        List<Product> result = promotion.apply(List.of());

        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldHandleNull() {
        assertDoesNotThrow(() -> promotion.apply(null));

        List<Product> result = promotion.apply(null);

        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldHandleListWithNull() {
        assertDoesNotThrow(() ->  promotion.apply(Arrays.asList(null, new Product("1", "mug", 500.0, 300), null)));
        
        List<Product> result = promotion.apply(Arrays.asList(null, new Product("1", "mug", 500.0, 300), null));

        double productsPrices = result.stream().mapToDouble(Product::getDiscountPrice).sum();

        assertEquals(300, productsPrices, 0.01);
    }

    @Test
    public void shouldHandleListWithOnlyNull() {
        List<Product> input = new ArrayList<>();
        input.add(null);

        assertDoesNotThrow(() ->  promotion.apply(input));

        List<Product> result = promotion.apply(input);

        double productsPrices = result.stream().mapToDouble(Product::getDiscountPrice).sum();

        assertEquals(0.0, productsPrices, 0.01);
    }


}