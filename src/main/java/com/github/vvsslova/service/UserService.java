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
 * Описание взаимодействия с пользователями
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
     * @param userID проверяемый ID
     * @throws UserAlreadyExistsException если пользователь уже существует
     */
    private void checkUserAbsence(String userID) throws UserAlreadyExistsException {
        if (users.containsKey(userID)) {
            throw new UserAlreadyExistsException();
        }
    }

    /**
     * Проверка отсутствия пользователя
     *
     * @param userID проверяемый ID
     * @throws UserNotFoundException если пользователь не найден
     */
    private void checkUsersAvailability(String userID) throws UserNotFoundException {
        if (!users.containsKey(userID)) {
            throw new UserNotFoundException();
        }
    }

    /**
     * Удаление пользователя
     *
     * @param userID ID удаляемого пользователя
     */
    protected void removeUser(String userID) {
        try {
            checkUsersAvailability(userID);
            users.remove(userID);
            log.info("Пользователь успешно удалён из библотеки");
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * Изменение номера телефона
     *
     * @param userID   ID изменяемого пользователя
     * @param newPhone новый номер
     */
    protected void changeUserPhone(String userID, long newPhone) {
        try {
            checkUsersAvailability(userID);
            UserDto changingUserDto = users.get(userID);
            changingUserDto.setPhoneNumber(newPhone);
            log.info("Номер телефона успешно изменён!");
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * Изменение пользовательского имени
     *
     * @param userID  ID пользователя, у которого необходимо изменить имя
     * @param newName новое имя
     */
    protected void changeUserName(String userID, String newName) {
        try {
            checkUsersAvailability(userID);
            UserDto changingUserDto = users.get(userID);
            changingUserDto.setName(newName);
            log.info("Имя успешно изменено!");
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * Изменение пользовательской фамилии
     *
     * @param userID     ID пользователя, у которого необходимо изменить фамилию
     * @param newSurName новая фамилия
     */
    protected void changeUserSurname(String userID, String newSurName) {
        try {
            checkUsersAvailability(userID);
            UserDto changingUserDto = users.get(userID);
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
