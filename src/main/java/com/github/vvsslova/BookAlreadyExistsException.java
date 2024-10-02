package com.github.vvsslova;

/**
 * Класс описывает исключение, выбрасываемое при попытке добавить в библиотеку уже существующую книгу
 */
public class BookAlreadyExistsException extends Throwable {
    public BookAlreadyExistsException() {
    }

    @Override
    public String getMessage() {
        return "This book already exists!";
    }
}
