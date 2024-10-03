package com.github.vvsslova.service;

import com.github.vvsslova.dto.UserDto;
import com.github.vvsslova.exception.UserAlreadyExistsException;
import com.github.vvsslova.exception.UserNotFoundException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс описывает взаимодействие с пользователями
 */
@Slf4j
@Data
public class UserService {
    private final Map<String, UserDto> users;

    public UserService() {
        this.users = new HashMap<>();
    }

    /**
     * Добавление пользователя
     *
     * @param userDto добавляемый пользователь
     */
    protected void addUser(UserDto userDto) {
        try {
            checkUserAbsence(userDto.getID());
            users.put(userDto.getID(), userDto);
            log.info("Пользователь {} успешно добавлен в библотеку", userDto);
        } catch (UserAlreadyExistsException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * Проверка наличия пользователя
     *
     * @param ID проверяемый ID
     * @throws UserAlreadyExistsException если пользователь уже существует
     */
    private void checkUserAbsence(String ID) throws UserAlreadyExistsException {
        if (users.containsKey(ID)) {
            throw new UserAlreadyExistsException();
        }
    }

    /**
     * Проверка отсутствия пользователя
     *
     * @param ID проверяемый ID
     * @throws UserNotFoundException если пользователь не найден
     */
    private void checkUsersAvailability(String ID) throws UserNotFoundException {
        if (!users.containsKey(ID)) {
            throw new UserNotFoundException();
        }
    }

    /**
     * Удаление пользователя
     *
     * @param userDto удаляемый пользователь
     */
    protected void removeUser(UserDto userDto) {
        try {
            checkUsersAvailability(userDto.getID());
            users.remove(userDto.getID());
            log.info("Пользователь {} успешно удалён из библотеки", userDto);
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * Изменение номера телефона
     *
     * @param ID       ID пользователя, у которого необходимо изменить номер
     * @param newPhone новый номер
     */
    protected void changeUserPhone(String ID, long newPhone) {
        try {
            checkUsersAvailability(ID);
            UserDto changingUserDto = users.get(ID);
            changingUserDto.setPhoneNumber(newPhone);
            log.info("Номер телефона успешно изменён!");
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * Изменение пользовательского имени
     *
     * @param ID      ID пользователя, у которого необходимо изменить имя
     * @param newName новое имя
     */
    protected void changeUserName(String ID, String newName) {
        try {
            checkUsersAvailability(ID);
            UserDto changingUserDto = users.get(ID);
            changingUserDto.setName(newName);
            log.info("Имя успешно изменено!");
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * Изменение пользовательской фамилии
     *
     * @param ID         ID пользователя, у которого необходимо изменить фамилию
     * @param newSurName новая фамилия
     */
    protected void changeUserSurname(String ID, String newSurName) {
        try {
            checkUsersAvailability(ID);
            UserDto changingUserDto = users.get(ID);
            changingUserDto.setSurname(newSurName);
            log.info("Фамилия успешно изменена!");
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * Получение списка всех пользователей
     */
    protected void printAllUsers() {
        List<UserDto> userDtoList = new ArrayList<>();
        for (Map.Entry<String, UserDto> entry : users.entrySet()) {
            userDtoList.add(entry.getValue());
        }
        log.info(userDtoList.toString());
    }
}
