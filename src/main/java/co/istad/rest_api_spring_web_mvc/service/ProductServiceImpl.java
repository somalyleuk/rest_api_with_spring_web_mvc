package co.istad.rest_api_spring_web_mvc.service;

import co.istad.rest_api_spring_web_mvc.dto.ProductRequest;
import co.istad.rest_api_spring_web_mvc.dto.ProductResponse;
import co.istad.rest_api_spring_web_mvc.exception.ApiException;
import co.istad.rest_api_spring_web_mvc.exception.ResourceNotFoundException;
import co.istad.rest_api_spring_web_mvc.mapper.ProductMapper;
import co.istad.rest_api_spring_web_mvc.entity.Product;
import co.istad.rest_api_spring_web_mvc.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepo;
    @Override
    public List<ProductResponse> findAllProducts() {
        return productRepo.findAll().stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }
    @Override
   public ProductResponse findProductById(Integer id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id.longValue()));
        return productMapper.toProductResponse(product);
    }
    @Override
    public ProductResponse createNewProduct(ProductRequest productRequest) {
        if (productRepo.existsByName(productRequest.name())) {
            throw new ApiException(HttpStatus.CONFLICT, "This Model with this name already exists!");
        }
        Product newProduct = productMapper.toProduct(productRequest);
        Product savedProduct = productRepo.save(newProduct);
        return productMapper.toProductResponse(savedProduct);
    }
    @Override
    public ProductResponse updateProductById(Integer id, ProductRequest productRequest) {
        if (productRepo.existsById(id)) {
            throw new ApiException(HttpStatus.CONFLICT, "This Model with this name already exists!");
        }
        Product productToUpdate = productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id.longValue()));
        productToUpdate.setName(productRequest.name());
        productToUpdate.setQuantity(productRequest.quantity());
        productToUpdate.setPrice(productRequest.price());
        return productMapper.toProductResponse(productToUpdate);
    }
    @Override
    public void deleteProductById(Integer id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id.longValue()));
        productRepo.deleteById(id);
    }
}