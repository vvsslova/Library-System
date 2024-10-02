package com.github.vvsslova;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * Класс описывает шаблон для добавления пользователя в библиотеку
 */
@Data
@Slf4j
public class User {
    private final String ID;
    private String name;
    private String surname;
    private long phoneNumber;

    public User(String name, String surname, long phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.ID = generateId();
    }

    /**
     * Генерация ID пользователя
     *
     * @return ID пользователя
     */
    private String generateId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
