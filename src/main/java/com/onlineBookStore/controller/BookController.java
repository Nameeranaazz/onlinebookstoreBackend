package com.onlineBookStore.controller;

import com.onlineBookStore.model.Book;
import com.onlineBookStore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // Update addBook to handle multipart form data
    @PostMapping("/add")
    public ResponseEntity<Book> addBook(
            @RequestParam("bookName") String bookName,
            @RequestParam("authorName") String authorName,
            @RequestParam("price") Double price,
            @RequestParam("category") String category,
            @RequestParam("image") MultipartFile image) throws IOException {

        String uploadDir = "src/main/resources/static/uploads/";
        String fileName = image.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);
        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        Book book = new Book();
        book.setBookName(bookName);
        book.setAuthorName(authorName);
        book.setPrice(price);
        book.setImageUrl(filePath.toString());
        book.setCategory(category);

        return new ResponseEntity<>(bookService.addBook(book), HttpStatus.CREATED);
    }

    @GetMapping("/category/{category}")
    public List<Book> getBooksByCategory(@PathVariable String category) {
        return bookService.getBooksByCategory(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        return new ResponseEntity<>(bookService.updateBook(id, book), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

