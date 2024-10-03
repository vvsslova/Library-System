package com.github.vvsslova.exception;

/**
 * Класс описывает исключение, выбрасываемое при попытке удалить несуществующую книгу
 */
public class BookNotFoundException extends Exception {
    @Override
    public String getMessage() {
        return "This book not found!";
    }
}
