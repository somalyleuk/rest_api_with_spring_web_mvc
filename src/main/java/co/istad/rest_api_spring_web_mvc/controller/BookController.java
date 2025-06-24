package co.istad.rest_api_spring_web_mvc.controller;


import co.istad.rest_api_spring_web_mvc.dto.ApiResponseDto;
import co.istad.rest_api_spring_web_mvc.model.Book;
import co.istad.rest_api_spring_web_mvc.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/books")
    public ResponseEntity<ApiResponseDto<List<Book>>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        ApiResponseDto<List<Book>> response = ApiResponseDto.success("Success", HttpStatus.OK, books);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<ApiResponseDto<Book>> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id)
                .map(book -> ResponseEntity.ok(
                        ApiResponseDto.success("Success", HttpStatus.OK, book))
                )
                .orElseGet(() -> {
                    ApiResponseDto<Book> response = ApiResponseDto.<Book>builder()
                            .message("Book not found with ID: " + id)
                            .status(HttpStatus.NOT_FOUND.value())
                            .data(null)
                            .timestamp(LocalDateTime.now())
                            .build();
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                });
    }

    @PostMapping("/create/book")
    public ResponseEntity<ApiResponseDto<Book>> createBook(@Valid @RequestBody Book book) {
        Book createdBook = bookService.addBook(book);
        ApiResponseDto<Book> response = ApiResponseDto.success("Book created successfully", HttpStatus.CREATED, createdBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("update/book/{id}")
    public ResponseEntity<ApiResponseDto<Book>> updateBook(@PathVariable Long id, @Valid @RequestBody Book book) {
        return bookService.updateBook(id, book)
                .map(updatedBook -> ResponseEntity.ok(
                        ApiResponseDto.success("Book updated successfully with ID: " + id, HttpStatus.OK, updatedBook))
                )
                .orElseGet(() -> {
                    ApiResponseDto<Book> response = ApiResponseDto.<Book>builder()
                            .message("Book not found! Update failed for book ID: " + id)
                            .status(HttpStatus.NOT_FOUND.value())
                            .data(null)
                            .timestamp(LocalDateTime.now())
                            .build();
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                });
    }

    @DeleteMapping("delete/book/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteBook(@PathVariable Long id) {
        if (bookService.deleteBook(id)) {
            return ResponseEntity.ok(ApiResponseDto.noContent("Book deleted successfully with ID: " + id, HttpStatus.OK));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponseDto.noContent("Book not found! Deletion failed for book ID: " + id, HttpStatus.NOT_FOUND));
    }
}

