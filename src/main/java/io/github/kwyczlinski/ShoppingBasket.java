package io.github.kwyczlinski;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ShoppingBasket implements Basket, ProductQuery {

    private List<Product> basket = new ArrayList<>();
    
    private static final Comparator<Product> DEFAULT_SORT = Comparator
            .comparingDouble(Product::getPrice).reversed()
            .thenComparing(Product::getName);

    private Comparator<Product> sortComparator = DEFAULT_SORT;

    public ShoppingBasket() {}

    @Override
    public void addProduct(Product product) {
        if (product == null) return;

        basket.add(product);
        sort();
    }

    public void sort() {
        basket.sort(sortComparator);
    }

    @Override
    public double getTotal() {
        return basket.stream().mapToDouble(Product::getDiscountPrice).sum();
    }

    @Override
    public void applyPromotions(List<Promotion> promotions) {

        if (promotions == null || promotions.isEmpty()) return;

        List<Product> result = basket.stream().map(Product::resetDiscount).toList();

        for (Promotion promotion : promotions) {
            if (promotion != null) {
                result = promotion.apply(result);
            }
        }

        basket = new ArrayList<>(result);
        sort();
    }

    @Override
    public void setSortComparator(Comparator<Product> comparator) {
        if (comparator != null) {
            sortComparator = comparator;
        }
    }

    @Override
    public List<Product> getProducts() {
        return List.copyOf(basket);
    }

    @Override
    public Optional<Product> getCheapestProduct() {
        return basket.stream().min(Comparator.comparingDouble(Product::getDiscountPrice));
    }

    @Override
    public Optional<Product> getPriciestProduct() {
        return basket.stream().max(Comparator.comparingDouble(Product::getDiscountPrice));
    }

    @Override
    public List<Product> getNCheapestProducts(int n) {
        return basket.stream()
                .sorted(Comparator.comparingDouble(Product::getDiscountPrice))
                .limit(n)
                .toList();
    }

    @Override
    public List<Product> getNPriciestProducts(int n) {
        return basket.stream()
                .sorted(Comparator.comparingDouble(Product::getDiscountPrice).reversed())
                .limit(n)
                .toList();
    }

    Comparator<Product> getSortComparator() { // for tests, package scope
        return sortComparator;
    }
}