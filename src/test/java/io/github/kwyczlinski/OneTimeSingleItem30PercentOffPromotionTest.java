package io.github.kwyczlinski;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/* Overview of the tested functionalities

 - most expensive product gets 30% off
 - two products with same price — only one gets discount (reference equality)
 - discount applies to discountPrice, not price
 - handles empty list correctly
 - does not throw when null passed as argument
 - handles null in argument list
*/

public class OneTimeSingleItem30PercentOffPromotionTest {
    private static final Promotion promotion = new OneTimeSingleItem30PercentOffPromotion();

    @Test
    public void shouldApplyWhenOneProduct() {
        List<Product> result = promotion.apply(List.of(new Product("1", "mug", 300.0)));

        double productPrice = result.getFirst().getDiscountPrice();

        assertEquals(210.0, productPrice);
    }

    @Test
    public void shouldApplyCorrectlyWhenMultipleProducts() {
        List<Product> result = promotion.
                                    apply(List.of(
                                        new Product("1", "mug", 30.0),
                                        new Product("2", "mouse", 25.0),
                                        new Product("3", "socks", 20.0)
                                    ));

        double productsPrices = result.stream().mapToDouble(Product::getDiscountPrice).sum();

        assertEquals(66.0, productsPrices, 0.01);
    }

    @Test
    public void shouldApplyDiscountToOnlyOneProduct() {
        List<Product> result = promotion.
                                    apply(List.of(
                                        new Product("1", "mug", 35.0, 5.0),
                                        new Product("2", "mouse", 25.0, 10.0),
                                        new Product("3", "socks", 20.0, 10.0)
                                    ));

        double productsPrices = result.stream().mapToDouble(Product::getDiscountPrice).sum();

        assertEquals(22.0, productsPrices, 0.01);
    }

    @Test
    public void shouldApplyToDiscountPrice() {
        List<Product> result = promotion.apply(List.of(new Product("1", "mug", 500.0, 300.0)));

        double productPrice = result.getFirst().getDiscountPrice();

        assertEquals(210.0, productPrice);
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

        assertEquals(210.0, productsPrices, 0.01);
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