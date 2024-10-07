package com.github.vvsslova.exception;

/**
 * Выбрасывается при попытке удалить взявшего книги пользователя
 */
public class UserLendBookException extends Exception{
    @Override
    public String getMessage() {
        return "У пользователя есть взятые книги!";
    }
}
