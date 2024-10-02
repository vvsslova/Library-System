package com.github.vvsslova;

/**
 * Класс описывает исключение, выбрасываемое при попытке дать пользователю ранее выданную книгу
 */
public class BookAlreadyLendException extends Throwable {
    public BookAlreadyLendException() {
    }

    @Override
    public String getMessage() {
        return "Эта книга уже выдана пользователю!";
    }
}
