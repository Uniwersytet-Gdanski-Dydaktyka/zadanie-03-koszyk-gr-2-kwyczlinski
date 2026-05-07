package io.github.kwyczlinski;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import org.junit.jupiter.api.Test;

/* Overview of the tested functionalities

 - withDiscountPrice() returns a new instance, original unchanged
 - resetDiscount() sets discountPrice back to price
 - constructor sets discountPrice == price by default
*/

public class ProductTest {

    @Test
    public void shouldInitCorrectValues() {
        Product product = new Product("COMP-MUG-OVER-200", "Company Mug", 50.0);

        assertEquals("COMP-MUG-OVER-200", product.getCode());
        assertEquals("Company Mug", product.getName());
        assertEquals(50.0, product.getPrice());
        assertEquals(50.0, product.getDiscountPrice());
    }

    @Test
    public void shouldInitCorrectValuesWithDiscount() {
        Product product = new Product("COMP-MUG-OVER-200", "Company Mug", 50.0, 20.0);

        assertEquals("COMP-MUG-OVER-200", product.getCode());
        assertEquals("Company Mug", product.getName());
        assertEquals(50.0, product.getPrice());
        assertEquals(20.0, product.getDiscountPrice());
    }

    @Test
    public void shouldNotChangeWhenDiscountUsed() {
        Product product = new Product("COMP-MUG-OVER-200", "Company Mug", 50.0);
        product.withDiscountPrice(20.0);

        assertEquals("COMP-MUG-OVER-200", product.getCode());
        assertEquals("Company Mug", product.getName());
        assertEquals(50.0, product.getPrice());
        assertEquals(50.0, product.getDiscountPrice());
    }

    @Test
    public void shouldReturnDiscountedProduct() {
        Product product = new Product("COMP-MUG-OVER-200", "Company Mug", 50.0);
        Product discounted = product.withDiscountPrice(20.0);

        assertNotSame(product, discounted);
        assertEquals("COMP-MUG-OVER-200", discounted.getCode());
        assertEquals("Company Mug", discounted.getName());
        assertEquals(50.0, discounted.getPrice());
        assertEquals(20.0, discounted.getDiscountPrice());
    }

    @Test
    public void shouldResetDiscountedPrice() {
        Product discounted = new Product("COMP-MUG-OVER-200", "Company Mug", 50.0)
                                .withDiscountPrice(20.0);
        Product product = discounted.resetDiscount();

        assertNotSame(discounted, product);
        assertEquals("COMP-MUG-OVER-200", product.getCode());
        assertEquals("Company Mug", product.getName());
        assertEquals(50.0, product.getPrice());
        assertEquals(50.0, product.getDiscountPrice());
    }
}
