package io.github.kwyczlinski;

import java.util.Comparator;
import java.util.List;

// Wyszukiwanie najtańszego/najdroższego produktu w zadanej kolekcji produktów
// Wyszukiwanie n najtańszych/najdroższych produktów w zadanej kolekcji produktów
// Sortowanie kolekcji produktów po cenie jak i po nazwie, a także otwarcie na sortowanie po dowolnych kryteriach w przyszłości
// Wyliczanie sumy cen wszystkich zadanych produktów
// Aplikowanie opisanych powyżej rodzajów promocji na zadanej kolekcji produktów w koszyku
// Umożliwiać dodawanie nowych promocji, o jeszcze nieznanych cechach, z zachowaniem zasad SOLID

public interface Basket {

    void addProduct(Product product);

    double getTotal();

    void applyPromotions(List<Promotion> promotions);

    List<Product> getProducts();

    void setSortComparator(Comparator<Product> comparator);
}
