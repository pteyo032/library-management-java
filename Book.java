package com.pierre.library;

import java.util.Objects;

public class Book {
    private final String id;        // unique identifier (e.g., ISBN or internal ID)
    private final String title;
    private final String author;
    private boolean available;

    public Book(String id, String title, String author) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("id cannot be empty");
        if (title == null || title.isBlank()) throw new IllegalArgumentException("title cannot be empty");
        if (author == null || author.isBlank()) throw new IllegalArgumentException("author cannot be empty");

        this.id = id.trim();
        this.title = title.trim();
        this.author = author.trim();
        this.available = true;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isAvailable() { return available; }

    public void borrow() {
        if (!available) throw new IllegalStateException("Book is already borrowed");
        available = false;
    }

    public void giveBack() {
        available = true;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s — %s (%s)", id, title, author, available ? "available" : "borrowed");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return id.equals(book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
