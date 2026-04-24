package isi.shoppingCart.usecases.services;

import isi.shoppingCart.entities.Cart;
import isi.shoppingCart.entities.CartItem;
import isi.shoppingCart.entities.Product;
import isi.shoppingCart.infrastructure.repositories.InMemoryCartRepository;
import isi.shoppingCart.infrastructure.repositories.InMemoryProductRepository;
import isi.shoppingCart.usecases.ports.CartRepository;
import isi.shoppingCart.usecases.ports.ProductRepository;
import java.util.List;

public class ShoppingCartApp {
    private ProductRepository productRepository;
    private CartRepository cartRepository;
    private AgregarProductoAlCarritoUseCase agregarProductoAlCarritoUseCase;

    public ShoppingCartApp() {
        productRepository = new InMemoryProductRepository();
        cartRepository = new InMemoryCartRepository();
        agregarProductoAlCarritoUseCase = new AgregarProductoAlCarritoUseCase(productRepository, cartRepository);

        initializeData();
    }

    public ShoppingCartApp(ProductRepository productRepository,
                           CartRepository cartRepository,
                           AgregarProductoAlCarritoUseCase agregarProductoAlCarritoUseCase) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.agregarProductoAlCarritoUseCase = agregarProductoAlCarritoUseCase;
    }

    private void initializeData() {
        loadCatalog();
        loadCart();
    }

    private void loadCatalog() {
        productRepository.save(new Product(1, "Laptop", 2500.0));
        productRepository.save(new Product(2, "Mouse", 80.0));
        productRepository.save(new Product(3, "Teclado", 150.0));
        productRepository.save(new Product(4, "Monitor", 900.0));
        productRepository.save(new Product(5, "Audifonos", 200.0));
        productRepository.save(new Product(6, "Webcam", 180.0));
    }

    private void loadCart() {
        Cart cart = new Cart();

        Product product1 = productRepository.findById(1);
        Product product2 = productRepository.findById(2);
        Product product3 = productRepository.findById(3);

        if (product1 != null) {
            cart.addProduct(product1);
        }

        if (product2 != null) {
            cart.addProduct(product2);
            cart.addProduct(product2);
        }

        if (product3 != null) {
            cart.addProduct(product3);
        }

        cartRepository.save(cart);
    }

    public List<Product> getCatalogProducts() {
        return productRepository.findAll();
    }

    public List<CartItem> getCartItems() {
        Cart cart = cartRepository.getCart();
        return cart.getItems();
    }

    public double getCartTotal() {
        Cart cart = cartRepository.getCart();
        return cart.getTotal();
    }

    public void addProductToCart(int productId) {
        agregarProductoAlCarritoUseCase.execute(productId);
    }
}
