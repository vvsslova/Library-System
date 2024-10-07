package com.github.vvsslova.exception;

/**
 * Выбрасывается при попытке добавить в библиотеку уже существующую книгу
 */
public class BookAlreadyExistsException extends Exception {
    @Override
    public String getMessage() {
        return "This book already exists!";
    }
}
