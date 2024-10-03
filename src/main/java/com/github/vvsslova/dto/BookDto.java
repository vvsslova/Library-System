package com.github.vvsslova.dto;

import com.github.vvsslova.ENUM.BookGenre;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * Класс описывает шаблон для добавления книги в библиотеку
 */
@Data
@Slf4j
public class BookDto {
    private final String ID;
    private String title;
    private String author;
    private BookGenre bookGenre;

    public BookDto(String title, String author, BookGenre bookGenre) {
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
        return UUID.randomUUID().toString();
    }
}
