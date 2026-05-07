package io.github.kwyczlinski;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/* Overview of the tested functionalities

 - total exactly 300 — no discount applied
 - total 300.01 — discount applied
 - discount applies to discountPrice, not price
 - stacks correctly — second promotion sees already-reduced prices [indirectly / Strategy pattern]
 - handles empty list correctly
 - does not throw when null passed as argument
 - handles null in argument list
*/

public class DiscountOver300PromotionTest {
    private static final Promotion promotion = new DiscountOver300Promotion();

    @Test
    public void shouldNotApplyWhenExacly300() {
        List<Product> result = promotion.apply(List.of(new Product("1", "mug", 300.0)));

        double productPrice = result.getFirst().getDiscountPrice();

        assertEquals(300.0, productPrice);
    }

    @Test
    public void shouldApplyCorrectly() {
        List<Product> result = promotion.apply(List.of(new Product("1", "mug", 300.01)));

        double productPrice = result.getFirst().getDiscountPrice();

        assertEquals(285.0, productPrice, 0.01);
    }

    @Test
    public void shouldApplyToDiscountPrice() {
        List<Product> result = promotion.apply(List.of(new Product("1", "mug", 500.0, 300.01)));

        double productPrice = result.getFirst().getDiscountPrice();

        assertEquals(285.0, productPrice, 0.01);
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
        List<Product> result = promotion.apply(Arrays.asList(null, new Product("1", "mug", 500.0, 300.01), null));

        double productsPrices = result.stream().mapToDouble(Product::getDiscountPrice).sum();

        assertEquals(285.0, productsPrices, 0.01);
    }

    @Test
    public void shouldHandleListWithOnlyNull() {
        List<Product> input = new ArrayList<>();
        input.add(null);

        List<Product> result = promotion.apply(input);

        double productsPrices = result.stream().mapToDouble(Product::getDiscountPrice).sum();

        assertEquals(0.0, productsPrices, 0.01);
    }


}