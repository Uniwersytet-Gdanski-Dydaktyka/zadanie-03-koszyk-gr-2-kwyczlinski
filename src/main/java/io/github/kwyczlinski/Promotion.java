package io.github.kwyczlinski;

import java.util.List;

public interface Promotion {
    public List<Product> apply(List<Product> products);
    default public String name() { return getClass().getSimpleName(); };
}