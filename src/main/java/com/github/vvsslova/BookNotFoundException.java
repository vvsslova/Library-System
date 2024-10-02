package com.github.vvsslova;

/**
 * Класс описывает исключение, выбрасываемое при попытке удалить несуществующую книгу
 */
public class BookNotFoundException extends Throwable {
    public BookNotFoundException() {
        super();
    }

    @Override
    public String getMessage() {
        return "This book not found!";
    }
}
