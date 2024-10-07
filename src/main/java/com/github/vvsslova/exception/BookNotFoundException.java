package com.github.vvsslova.exception;

/**
 * Выбрасывается при попытке удалить несуществующую книгу
 */
public class BookNotFoundException extends Exception {
    @Override
    public String getMessage() {
        return "This book not found!";
    }
}
