package com.github.vvsslova.exception;

/**
 * Класс описывает исключение, выбрасываемое при попытке удалить несуществующего пользователя
 */
public class UserNotFoundException extends Exception {
    @Override
    public String getMessage() {
        return "This user not found!";
    }
}
