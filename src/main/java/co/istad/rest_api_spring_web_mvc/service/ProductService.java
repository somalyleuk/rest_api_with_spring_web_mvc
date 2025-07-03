package co.istad.rest_api_spring_web_mvc.service;

import co.istad.rest_api_spring_web_mvc.dto.ProductRequest;
import co.istad.rest_api_spring_web_mvc.dto.ProductResponse;
import java.util.List;

public interface ProductService {
    List<ProductResponse> findAllProducts();
    ProductResponse findProductById(Integer id);
    ProductResponse createNewProduct(ProductRequest productRequest);
    ProductResponse updateProductById(Integer id, ProductRequest productRequest);
    void deleteProductById(Integer id);
}

