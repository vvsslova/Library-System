package com.github.vvsslova;

import com.github.vvsslova.ENUM.BookGenre;
import com.github.vvsslova.dto.BookDto;
import com.github.vvsslova.dto.UserDto;
import com.github.vvsslova.service.LibraryService;

/**
 * Тестовый класс
 */
public class App {
    public static void main(String[] args) {
        UserDto userDto1 = new UserDto("Victoria", "Shilova", 79005830462L);
        UserDto userDto2 = new UserDto("Natalia", "Shilova", 79042600860L);
        BookDto bookDto1 = new BookDto("Pride and Prejudice", "Jane Osten", BookGenre.ROMANCE);
        LibraryService libraryService1 = new LibraryService("Moscow State University Library");
        libraryService1.addBook(bookDto1);
        libraryService1.addUser(userDto1);
        libraryService1.addUser(userDto2);
        libraryService1.printAllUsers();
        libraryService1.removeUser(userDto2);
        libraryService1.addUser(userDto2);
        libraryService1.printAllUsers();
        libraryService1.changeUserPhone(userDto2, 79042600861L);
        libraryService1.printAllUsers();
        libraryService1.addBook(bookDto1);
        BookDto bookDto2 = new BookDto("Flowers for Algernon", "Daniel Kie", BookGenre.DRAMA);
        libraryService1.addBook(bookDto2);
        libraryService1.changeBookAuthor(bookDto2, "Keyes");
        libraryService1.printAllBooks();
        libraryService1.printFoundBook("Flowers for Algernon", null);
        libraryService1.printFoundBook(null, "Jane Osten");
        libraryService1.lendBook(bookDto2, userDto1, 14);
        libraryService1.returnBook(bookDto2, userDto1);
    }
}
