package com.onlineBookStore.service;

import com.onlineBookStore.model.Book;
import com.onlineBookStore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getBooksByCategory(String category) {
        return bookRepository.findByCategory(category);
    }

    public Book updateBook(Long id, Book updatedBook) {
        return bookRepository.findById(id).map(book -> {
            book.setBookName(updatedBook.getBookName());
            book.setAuthorName(updatedBook.getAuthorName());
            book.setPrice(updatedBook.getPrice());
            book.setImageUrl(updatedBook.getImageUrl());
            return bookRepository.save(book);
        }).orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}

