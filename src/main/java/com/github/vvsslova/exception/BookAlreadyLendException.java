package com.github.vvsslova.exception;

/**
 * Выбрасывается при попытке дать пользователю ранее выданную книгу
 */
public class BookAlreadyLendException extends Exception {
    @Override
    public String getMessage() {
        return "Эта книга уже выдана пользователю!";
    }
}
