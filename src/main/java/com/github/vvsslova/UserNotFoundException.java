package com.github.vvsslova;

/**
 * Класс описывает исключение, выбрасываемое при попытке удалить несуществующего пользователя
 */
public class UserNotFoundException extends Throwable {
    public UserNotFoundException() {
    }

    @Override
    public String getMessage() {
        return "This user not found!";
    }
}
