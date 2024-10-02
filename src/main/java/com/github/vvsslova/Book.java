package com.github.vvsslova;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * Класс описывает шаблон для добавления книги в библиотеку
 */
@Data
@Slf4j
public class Book {
    private final String ID;
    private String title;
    private String author;
    private BookGenre bookGenre;

    public Book(String title, String author, BookGenre bookGenre) {
        this.ID = generateId();
        this.title = title;
        this.author = author;
        this.bookGenre = bookGenre;
    }

    /**
     * Генерация ID книги
     *
     * @return ID книги
     */
    private String generateId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
