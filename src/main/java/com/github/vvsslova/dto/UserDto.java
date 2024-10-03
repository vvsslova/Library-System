package com.github.vvsslova.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * Класс описывает шаблон для добавления пользователя в библиотеку
 */
@Data
@Slf4j
public class UserDto {
    private final String ID;
    private String name;
    private String surname;
    private long phoneNumber;

    public UserDto(String name, String surname, long phoneNumber) {
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
        return UUID.randomUUID().toString();
    }
}
