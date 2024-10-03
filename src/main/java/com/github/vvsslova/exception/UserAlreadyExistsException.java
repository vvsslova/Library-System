package com.github.vvsslova.exception;

/**
 * Класс описывает исключение, выбрасываемое при попытке добавить уже существующего пользователя
 */
public class UserAlreadyExistsException extends Exception {
    @Override
    public String getMessage() {
        return "This user already exists!";
    }
}
