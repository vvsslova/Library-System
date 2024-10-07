package com.github.vvsslova.exception;

/**
 * Выбрасывается при попытке удалить несуществующего пользователя
 */
public class UserNotFoundException extends Exception {
    @Override
    public String getMessage() {
        return "This user not found!";
    }
}
