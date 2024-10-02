package com.github.vvsslova;

/**
 * Тестовый класс
 */
public class App {
    public static void main(String[] args) {
        User user1 = new User("Victoria", "Shilova", 79005830462L);
        User user2 = new User("Natalia", "Shilova", 79042600860L);
        Book book1 = new Book("Pride and Prejudice", "Jane Osten", BookGenre.ROMANCE);
        Library library1 = new Library("Moscow State University Library");
        library1.addBook(book1);
        library1.addUser(user1);
        library1.addUser(user2);
        library1.getAllUsers();
        library1.removeUser(user2);
        library1.addUser(user2);
        library1.getAllUsers();
        library1.changeUserPhone(user2, 79042600861L);
        library1.getAllUsers();
        library1.addBook(book1);
        Book book2 = new Book("Flowers for Algernon", "Daniel Kie", BookGenre.DRAMA);
        library1.addBook(book2);
        library1.changeBookAuthor(book2, "Keyes");
        library1.getAllBooks();
        library1.searchBooks("Flowers for Algernon", null);
        library1.searchBooks(null, "Jane Osten");
        library1.lendBook(book2, user1, 14);
        library1.returnBook(book2, user1);
    }
}
