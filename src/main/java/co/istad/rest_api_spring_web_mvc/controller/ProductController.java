package co.istad.rest_api_spring_web_mvc.controller;

import co.istad.rest_api_spring_web_mvc.dto.Response; // MODIFIED: Import from dto package
import co.istad.rest_api_spring_web_mvc.dto.ProductRequest;
import co.istad.rest_api_spring_web_mvc.dto.ProductResponse;
import co.istad.rest_api_spring_web_mvc.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
      @GetMapping("/products")
    public ResponseEntity<Response<List<ProductResponse>>> getAllProducts() {
        List<ProductResponse> products = productService.findAllProducts();
        Response<List<ProductResponse>> response = Response.<List<ProductResponse>>builder()
                .message("Products retrieved successfully.")
                .status(HttpStatus.OK.value())
                .data(products)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("product/{id}")
    public ResponseEntity<Response<ProductResponse>> getProductById(@PathVariable Integer id) {
        ProductResponse product = productService.findProductById(id);
        Response<ProductResponse> response = Response.<ProductResponse>builder()
                .message("Product retrieved successfully.")
                .status(HttpStatus.OK.value())
                .data(product)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("create/product")
    public ResponseEntity<Response<ProductResponse>> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        ProductResponse newProduct = productService.createNewProduct(productRequest);
        Response<ProductResponse> response = Response.<ProductResponse>builder()
                .message("Product created successfully.")
                .status(HttpStatus.CREATED.value())
                .data(newProduct)
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PutMapping("update/product/{id}")
    public ResponseEntity<Response<ProductResponse>> updateProduct(@PathVariable Integer id, @Valid @RequestBody ProductRequest productRequest) {
        ProductResponse updatedProduct = productService.updateProductById(id, productRequest);
        Response<ProductResponse> response = Response.<ProductResponse>builder()
                .message("Product updated successfully.")
                .status(HttpStatus.OK.value())
                .data(updatedProduct)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("delete/product/{id}")
    public ResponseEntity<Response<Void>> deleteProduct(@PathVariable Integer id) {
        productService.deleteProductById(id);
        Response<Void> response = Response.<Void>builder()
                .message("Product deleted successfully.")
                .status(HttpStatus.NO_CONTENT.value())
                .data(null)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.noContent().build();
    }
}