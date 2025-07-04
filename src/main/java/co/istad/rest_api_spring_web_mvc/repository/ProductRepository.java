package co.istad.rest_api_spring_web_mvc.repository;

import co.istad.rest_api_spring_web_mvc.entity.Product;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class ProductRepository {
    private final List<Product> products = new ArrayList<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    @PostConstruct
    void init() {
        products.add(new Product(nextId.getAndIncrement(), "iPhone 15 Pro", 10, 999.99));
        products.add(new Product(nextId.getAndIncrement(), "Samsung Galaxy S24 Ultra", 5, 1299.99));
    }
    public List<Product> findAll() {
        return new ArrayList<>(products);
    }
    public Optional<Product> findById(Integer id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }
    public Product save(Product product) {
        if (product.getId() == null) {
            product.setId(nextId.getAndIncrement());
            products.add(product);
        } else {
            products.replaceAll(p -> p.getId().equals(product.getId()) ? product : p);
        }
        return product;
    }

    public void deleteById(Integer id) {
        products.removeIf(product -> product.getId().equals(id));
    }
    public boolean existsById(Integer id) {
        return products.stream().anyMatch(p -> p.getId().equals(id));
    }

    public boolean existsByName(String name) {
        return products.stream()
                .anyMatch(p -> p.getName().equalsIgnoreCase(name.trim()));
    }
}