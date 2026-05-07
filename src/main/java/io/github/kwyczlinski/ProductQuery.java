package io.github.kwyczlinski;

import java.util.List;
import java.util.Optional;

public interface ProductQuery {

    Optional<Product> getCheapestProduct();

    Optional<Product> getPriciestProduct();

    List<Product> getNCheapestProducts(int n);
    
    List<Product> getNPriciestProducts(int n);

}