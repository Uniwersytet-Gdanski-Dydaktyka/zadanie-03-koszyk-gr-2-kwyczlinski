package io.github.kwyczlinski;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/* Overview of the tested functionalities

 - calling applyPromotions() twice gives same result as calling once (idempotency)
 - applyPromotions(null) does not throw
 - applyPromotions with list containing null promotion does not throw
 - applyPromotions on empty basket does nothing
 - applyPromotions with empty list does nothing
*/

public class ShoppingBasketPromotionsTest {
    ShoppingBasket basket;

    @BeforeEach
    public void createBasket() {
        basket = new ShoppingBasket();
    }

    @Test
    public void shouldApplyPromotionsBeIdempotent() {
        basket.addProduct(new Product("1", "mug", 10.0));
        
        basket.applyPromotions(List.of(new OneTimeSingleItem30PercentOffPromotion()));
        basket.applyPromotions(List.of(new OneTimeSingleItem30PercentOffPromotion()));

        assertEquals(7.0, basket.getTotal());
    }

    @Test
    public void shouldHandleApplyPromotionsNull() {
        basket.addProduct(new Product("1", "mug", 10.0));
        assertDoesNotThrow(() -> basket.applyPromotions(null));
        assertEquals(10.0, basket.getTotal());
    }
    
    @Test
    public void shouldHandleApplyPromotionsWithNullInList() {
        basket.addProduct(new Product("1", "mug", 10.0));
        List<Promotion> promotions = new ArrayList<>();
        promotions.add(null);
        promotions.add(new OneTimeSingleItem30PercentOffPromotion());
        
        assertDoesNotThrow(() -> basket.applyPromotions(promotions));
        assertEquals(7.0, basket.getTotal());
    }

    @Test
    public void shouldHandleApplyPromotionsOnEmptyBasket() {
        assertDoesNotThrow(() -> basket.applyPromotions(List.of(new OneTimeSingleItem30PercentOffPromotion())));
    }

    @Test
    public void shouldApplyPromotionsHandleEmptyList() {
        assertDoesNotThrow(() -> basket.applyPromotions(List.of()));
    }
    
}

