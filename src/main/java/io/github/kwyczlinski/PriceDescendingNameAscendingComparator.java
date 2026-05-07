package io.github.kwyczlinski;

import java.util.Comparator;

public class PriceDescendingNameAscendingComparator implements Comparator<Product>{
    
    @Override
    public int compare(Product p1, Product p2) {
        return Comparator
            .comparingDouble(Product::getPrice).reversed()
            .thenComparing(Product::getName)
            .compare(p1, p2);
    }
}