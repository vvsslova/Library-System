package com.github.vvsslova.exception;

/**
 * Выбрасывается при попытке добавить уже существующего пользователя
 */
public class UserAlreadyExistsException extends Exception {
    @Override
    public String getMessage() {
        return "This user already exists!";
    }
}
