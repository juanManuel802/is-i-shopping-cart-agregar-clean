package isi.shoppingCart.usecases.services;

import isi.shoppingCart.entities.Cart;
import isi.shoppingCart.entities.CartItem;
import isi.shoppingCart.entities.Purchase;
import isi.shoppingCart.usecases.ports.CartRepository;
import isi.shoppingCart.usecases.ports.PurchaseRepository;

public class ConfirmarCompraUseCase {
    private CartRepository cartRepository;
    private PurchaseRepository purchaseRepository;

    public ConfirmarCompraUseCase(CartRepository cartRepository,
                                  PurchaseRepository purchaseRepository) {
        this.cartRepository = cartRepository;
        this.purchaseRepository = purchaseRepository;
    }

    public Purchase execute() {
        Cart cart = cartRepository.getCart();

        if (cart.isEmpty()) {
            return null;
        }

        Purchase purchase = new Purchase(cart.getItems(), cart.getTotal());

        for (int i = 0; i < cart.getItems().size(); i++) {
            CartItem item = cart.getItems().get(i);
            item.getProduct().decreaseAvailableQuantity(item.getQuantity());
        }

        purchaseRepository.save(purchase);

        cart.clear();
        cartRepository.save(cart);

        return purchase;
    }
}
