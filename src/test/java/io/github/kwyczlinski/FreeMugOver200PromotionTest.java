package io.github.kwyczlinski;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/* Overview of the tested functionalities

 - total exactly 200 — no mug added
 - total 200.01 — mug added
 - mug not added twice when called twice
 - mug removed if total drops to ≤ 200 on second call

 - discount applies to when discountPrice, not price is over 200
 - handles empty list correctly
 - does not throw when null passed as argument
 - handles null in argument list
*/

public class FreeMugOver200PromotionTest {
    private static final Promotion promotion = new FreeMugOver200Promotion();

        @Test
    public void shouldNotApplyWhenExacly200() {
        List<Product> result = promotion.apply(List.of(new Product("1", "mug", 200.0)));

        assertEquals(1, result.size());
    }

    @Test
    public void shouldApplyCorrectly() {
        List<Product> result = promotion.apply(List.of(new Product("1", "mug", 200.01)));

        assertTrue(result.stream().anyMatch(p -> "COMP-MUG-OVER-200".equals(p.getCode())));
        assertEquals(2, result.size());
    }

    @Test
    public void shouldApplyOnlyOnceEvenWhenCalledMultiple() {
        List<Product> helper = promotion.apply(List.of(new Product("1", "mug", 200.01)));
        List<Product> result = promotion.apply(helper);

        assertEquals(2, result.size());
    }

    @Test
    public void shouldRemoveMugWhenTotalDrops() {
        List<Product> result = promotion.apply(List.of(new Product("1", "mug", 20.0), new Product("COMP-MUG-OVER-200", "Company Mug", 0.0)));
    
        assertFalse(result.stream().anyMatch(p -> "COMP-MUG-OVER-200".equals(p.getCode())));
        assertEquals(1, result.size());
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