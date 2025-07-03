package co.istad.rest_api_spring_web_mvc.service;

import co.istad.rest_api_spring_web_mvc.dto.ProductRequest;
import co.istad.rest_api_spring_web_mvc.dto.ProductResponse;
import co.istad.rest_api_spring_web_mvc.exception.ApiException;
import co.istad.rest_api_spring_web_mvc.exception.ResourceNotFoundException;
import co.istad.rest_api_spring_web_mvc.mapper.ProductMapper;
import co.istad.rest_api_spring_web_mvc.model.Product;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final List<Product> products = new ArrayList<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    @PostConstruct
    void init() {
        products.add(new Product(nextId.getAndIncrement(), "Apple iPhone 15 Pro", 10, 999.99));
        products.add(new Product(nextId.getAndIncrement(), "Samsung Galaxy S24 Ultra", 5, 1299.99));
        products.add(new Product(nextId.getAndIncrement(), "Google Pixel 8 Pro", 8, 899.00));
    }
    @Override
    public List<ProductResponse> findAllProducts() {
        return products.stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }
    @Override
    public ProductResponse findProductById(Integer id) {
        Product product = products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product", id.longValue()));
        return productMapper.toProductResponse(product);
    }
    @Override
    public ProductResponse createNewProduct(ProductRequest productRequest) {
        boolean isNameTaken = products.stream()
                .anyMatch(p -> p.getName().equalsIgnoreCase(productRequest.name()));
        if (isNameTaken) {
            throw new ApiException(HttpStatus.CONFLICT, "This Model with this name is already exists !");
        }
        Product newProduct = productMapper.toProduct(productRequest);
        newProduct.setId(nextId.getAndIncrement());
        products.add(newProduct);
        return productMapper.toProductResponse(newProduct);
    }
    @Override
    public ProductResponse updateProductById(Integer id, ProductRequest productRequest) {
        Product productToUpdate = products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product", id.longValue()));

        boolean isNameTakenByAnother = products.stream()
                .filter(p -> !p.getId().equals(id))
                .anyMatch(p -> p.getName().equalsIgnoreCase(productRequest.name()));
        if (isNameTakenByAnother) {
            throw new ApiException(HttpStatus.CONFLICT, "This Model with this name is already exists !");
        }
        productToUpdate.setName(productRequest.name());
        productToUpdate.setQuantity(productRequest.quantity());
        productToUpdate.setPrice(productRequest.price());

        return productMapper.toProductResponse(productToUpdate);
    }
    @Override
    public void deleteProductById(Integer id) {
        Product product = products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Product", id.longValue()));
        products.remove(product);
    }
}