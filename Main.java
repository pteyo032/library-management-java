package com.pierre.library;

import com.pierre.library.exceptions.BookNotAvailableException;
import com.pierre.library.exceptions.BookNotFoundException;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static void seed(Library library) {
        library.addBook(new Book("B101", "Clean Code", "Robert C. Martin"));
        library.addBook(new Book("B102", "The Pragmatic Programmer", "Andrew Hunt"));
        library.addBook(new Book("B103", "Introduction to Algorithms", "Cormen et al."));
    }

    public static void main(String[] args) {
        Library library = new Library();
        seed(library);

        Scanner sc = new Scanner(System.in);

        System.out.println("=== Library Management (Java CLI) ===");
        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("Choice: ");
            String choice = sc.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        handleList(library);
                        break;
                    case "2":
                        handleAdd(library, sc);
                        break;
                    case "3":
                        handleSearch(library, sc);
                        break;
                    case "4":
                        handleRemove(library, sc);
                        break;
                    case "5":
                        handleBorrow(library, sc);
                        break;
                    case "6":
                        handleReturn(library, sc);
                        break;
                    case "0":
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Input error: " + e.getMessage());
            } catch (BookNotFoundException | BookNotAvailableException e) {
                System.out.println("Library error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }

            System.out.println();
        }

        System.out.println("Goodbye!");
        sc.close();
    }

    private static void printMenu() {
        System.out.println("1) List books");
        System.out.println("2) Add a book");
        System.out.println("3) Search books");
        System.out.println("4) Remove a book");
        System.out.println("5) Borrow a book");
        System.out.println("6) Return a book");
        System.out.println("0) Exit");
    }

    private static void handleList(Library library) {
        List<Book> books = library.listBooks();
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
            return;
        }
        books.forEach(System.out::println);
    }

    private static void handleAdd(Library library, Scanner sc) {
        System.out.print("Book ID: ");
        String id = sc.nextLine();
        System.out.print("Title: ");
        String title = sc.nextLine();
        System.out.print("Author: ");
        String author = sc.nextLine();

        library.addBook(new Book(id, title, author));
        System.out.println("Book added.");
    }

    private static void handleSearch(Library library, Scanner sc) {
        System.out.print("Search query: ");
        String q = sc.nextLine();
        List<Book> results = library.search(q);
        if (results.isEmpty()) {
            System.out.println("No results.");
            return;
        }
        results.forEach(System.out::println);
    }

    private static void handleRemove(Library library, Scanner sc) {
        System.out.print("Book ID to remove: ");
        String id = sc.nextLine().trim();
        boolean removed = library.removeBook(id);
        System.out.println(removed ? "Book removed." : "Book not found.");
    }

    private static void handleBorrow(Library library, Scanner sc) {
        System.out.print("Book ID to borrow: ");
        String id = sc.nextLine().trim();
        library.borrowBook(id);
        System.out.println("Book borrowed.");
    }

    private static void handleReturn(Library library, Scanner sc) {
        System.out.print("Book ID to return: ");
        String id = sc.nextLine().trim();
        library.returnBook(id);
        System.out.println("Book returned.");
    }
}
