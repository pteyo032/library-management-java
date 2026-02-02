package com.pierre.library;

import com.pierre.library.exceptions.BookNotAvailableException;
import com.pierre.library.exceptions.BookNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {

    @Test
    void addAndListBooks() {
        Library lib = new Library();
        lib.addBook(new Book("1", "A", "X"));
        lib.addBook(new Book("2", "B", "Y"));
        assertEquals(2, lib.size());
        assertEquals(2, lib.listBooks().size());
    }

    @Test
    void borrowAndReturnBook() {
        Library lib = new Library();
        lib.addBook(new Book("1", "A", "X"));

        lib.borrowBook("1");
        assertFalse(lib.getBookOrThrow("1").isAvailable());

        lib.returnBook("1");
        assertTrue(lib.getBookOrThrow("1").isAvailable());
    }

    @Test
    void borrowUnavailableThrows() {
        Library lib = new Library();
        lib.addBook(new Book("1", "A", "X"));
        lib.borrowBook("1");
        assertThrows(BookNotAvailableException.class, () -> lib.borrowBook("1"));
    }

    @Test
    void unknownBookThrows() {
        Library lib = new Library();
        assertThrows(BookNotFoundException.class, () -> lib.borrowBook("does-not-exist"));
    }
}
