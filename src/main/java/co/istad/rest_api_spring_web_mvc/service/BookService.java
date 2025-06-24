package co.istad.rest_api_spring_web_mvc.service;

import co.istad.rest_api_spring_web_mvc.exception.DuplicateBookException;
import co.istad.rest_api_spring_web_mvc.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookService {
    private final List<Book> books = new ArrayList<>();
    private final AtomicLong idAuto = new AtomicLong();

    public BookService() {
        books.add(new Book(idAuto.incrementAndGet(), "Spring in Action", "Craig Walls", 29.99));
        books.add(new Book(idAuto.incrementAndGet(), "Java Programming", "John Smith", 35.99));
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public Optional<Book> getBookById(Long id) {
        return books.stream().filter(book -> book.getId().equals(id)).findFirst();
    }

    public Book addBook(Book book) {
        boolean isDuplicate = books.stream().anyMatch(existingBook -> existingBook.getTitle().equalsIgnoreCase(book.getTitle()) && existingBook.getAuthor().equalsIgnoreCase(book.getAuthor()));
        if (isDuplicate) {
            throw new DuplicateBookException("A book with title '" + book.getTitle() + "' and author '" + book.getAuthor() + "' already exists.");
        }
        book.setId(idAuto.incrementAndGet());
        books.add(book);
        return book;
    }


    public Optional<Book> updateBook(Long id, Book updatedBook) {
        Optional<Book> existingBook = getBookById(id);
        if (existingBook.isPresent()) {
            int index = -1;
            for (int i = 0; i < books.size(); i++) {
                if (books.get(i).getId().equals(id)) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                updatedBook.setId(id);
                books.set(index, updatedBook);
                return Optional.of(updatedBook);
            }
        }
        return Optional.empty();
    }


    public boolean deleteBook(Long id) {
        return books.removeIf(book -> book.getId().equals(id));
    }
}