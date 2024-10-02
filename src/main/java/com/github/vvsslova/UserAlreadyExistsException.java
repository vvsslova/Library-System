package com.github.vvsslova;

/**
 * Класс описывает исключение, выбрасываемое при попытке добавить уже существующего пользователя
 */
public class UserAlreadyExistsException extends Throwable {
    public UserAlreadyExistsException() {
    }

    @Override
    public String getMessage() {
        return "This user already exists!";
    }
}
