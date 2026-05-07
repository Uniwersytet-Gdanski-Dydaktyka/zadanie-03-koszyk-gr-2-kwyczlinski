## Product

withDiscountPrice() returns a new instance, original unchanged
resetDiscount() sets discountPrice back to price
constructor sets discountPrice == price by default


## ShoppingBasket — basic

basketValue() on empty basket returns 0.0
basketValue() sums discountPrice, not price
addProduct(null) does not throw, basket unchanged
getProducts() returns defensive copy — mutating it does not affect basket
getProducts() returns products sorted price descending, then name ascending
default sort: price descending, then name ascending for equal prices
setSortComparator(null) does not throw, previous comparator unchanged
setSortComparator() changes sort order correctly

## ShoppingBasket — search

getCheapestProduct() on empty basket returns Optional.empty()
getPriciestProduct() on empty basket returns Optional.empty()
getCheapestProduct() returns correct product regardless of insertion order
getNCheapestProducts(0) returns empty list
getNCheapestProducts(n) where n > basket.size() returns all products
getNPriciestProducts symmetric equivalents of above
results reflect discountPrice, not price


## ShoppingBasket — applyPromotions

calling applyPromotions() twice gives same result as calling once (idempotency)
applyPromotions(null) does not throw
applyPromotions with list containing null promotion does not throw
applyPromotions on empty basket does nothing


## DiscountOver300Promotion

total exactly 300 — no discount applied
total 300.01 — discount applied
discount applies to discountPrice, not price
stacks correctly — second promotion sees already-reduced prices
handles empty list correctly
does not throw when null passed as argument
handles null in argument list


## ThirdProductFreePromotion

fewer than 3 products — no change
exactly 3 products — cheapest becomes free
4+ products — only cheapest becomes free, others unchanged
two products with identical price — only one becomes free (reference equality)
free means discountPrice = 0, not price = 0


## FreeMugOver200Promotion

total exactly 200 — no mug added
total 200.01 — mug added
mug not added twice when called twice
mug removed if total drops to ≤ 200 on second call


## OneTimeSingleItem30PercentOffPromotion

most expensive product gets 30% off
other products unchanged
two products with same price — only one gets discount (reference equality)
