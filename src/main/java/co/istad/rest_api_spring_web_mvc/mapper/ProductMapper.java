package co.istad.rest_api_spring_web_mvc.mapper;

import co.istad.rest_api_spring_web_mvc.dto.ProductRequest;
import co.istad.rest_api_spring_web_mvc.dto.ProductResponse;
import co.istad.rest_api_spring_web_mvc.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .build();
    }
    public Product toProduct(ProductRequest productRequest) {
        Product product = new Product();
        product.setName(productRequest.name());
        product.setQuantity(productRequest.quantity());
        product.setPrice(productRequest.price());
        return product;
    }
}