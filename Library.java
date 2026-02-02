package com.pierre.library;

import com.pierre.library.exceptions.BookNotAvailableException;
import com.pierre.library.exceptions.BookNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

public class Library {
    private final Map<String, Book> booksById = new HashMap<>();

    public void addBook(Book book) {
        Objects.requireNonNull(book, "book cannot be null");
        booksById.put(book.getId(), book);
    }

    public boolean removeBook(String id) {
        return booksById.remove(id) != null;
    }

    public List<Book> listBooks() {
        return booksById.values()
                .stream()
                .sorted(Comparator.comparing(Book::getTitle))
                .collect(Collectors.toList());
    }

    public Book getBookOrThrow(String id) {
        Book book = booksById.get(id);
        if (book == null) throw new BookNotFoundException("Book not found: " + id);
        return book;
    }

    public void borrowBook(String id) {
        Book book = getBookOrThrow(id);
        if (!book.isAvailable()) throw new BookNotAvailableException("Book is already borrowed: " + id);
        book.borrow();
    }

    public void returnBook(String id) {
        Book book = getBookOrThrow(id);
        book.giveBack();
    }

    public List<Book> search(String query) {
        String q = (query == null) ? "" : query.trim().toLowerCase();
        return booksById.values().stream()
                .filter(b ->
                        b.getTitle().toLowerCase().contains(q) ||
                        b.getAuthor().toLowerCase().contains(q) ||
                        b.getId().toLowerCase().contains(q)
                )
                .sorted(Comparator.comparing(Book::getTitle))
                .collect(Collectors.toList());
    }

    public int size() {
        return booksById.size();
    }
}
