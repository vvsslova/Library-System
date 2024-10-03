package com.github.vvsslova.exception;

/**
 * Класс описывает исключение, выбрасываемое при попытке добавить в библиотеку уже существующую книгу
 */
public class BookAlreadyExistsException extends Exception {
    @Override
    public String getMessage() {
        return "This book already exists!";
    }
}
