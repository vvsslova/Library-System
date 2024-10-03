package com.github.vvsslova.exception;

/**
 * Класс описывает исключение, выбрасываемое при попытке дать пользователю ранее выданную книгу
 */
public class BookAlreadyLendException extends Exception {
    @Override
    public String getMessage() {
        return "Эта книга уже выдана пользователю!";
    }
}
