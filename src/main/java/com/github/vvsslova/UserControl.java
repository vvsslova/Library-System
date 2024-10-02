package com.github.vvsslova;

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
public class UserControl {
    private final Map<String, User> users;

    public UserControl() {
        this.users = new HashMap<>();
    }

    /**
     * Добавление пользователя
     *
     * @param user добавляемый пользователь
     */
    protected void addUser(User user) {
        try {
            checkUserAbsence(user);
            users.put(user.getID(), user);
            log.info("Пользователь {} успешно добавлен в библотеку", user);
        } catch (UserAlreadyExistsException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * Проверка наличия пользователя
     *
     * @param user проверяемый пользователь
     * @throws UserAlreadyExistsException если пользователь уже существует
     */
    private void checkUserAbsence(User user) throws UserAlreadyExistsException {
        if (users.containsKey(user.getID())) {
            throw new UserAlreadyExistsException();
        }
    }

    /**
     * Проверка отсутствия пользователя
     *
     * @param user проверяемый пользователь
     * @throws UserNotFoundException если пользователь не найден
     */
    private void checkUsersAvailability(User user) throws UserNotFoundException {
        if (!users.containsKey(user.getID())) {
            throw new UserNotFoundException();
        }
    }

    /**
     * Удаление пользователя
     *
     * @param user удаляемый пользователь
     */
    protected void removeUser(User user) {
        try {
            checkUsersAvailability(user);
            users.remove(user.getID());
            log.info("Пользователь {} успешно удалён из библотеки", user);
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * Изменение номера телефона
     *
     * @param user     пользователь, у которого необходимо изменить номер
     * @param newPhone новый номер
     */
    protected void changeUserPhone(User user, long newPhone) {
        try {
            checkUsersAvailability(user);
            User changingUser = users.get(user.getID());
            changingUser.setPhoneNumber(newPhone);
            log.info("Номер телефона успешно изменён!");
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * Изменение пользовательского имени
     *
     * @param user    пользователь, у которого необходимо изменить имя
     * @param newName новое имя
     */
    protected void changeUserName(User user, String newName) {
        try {
            checkUsersAvailability(user);
            User changingUser = users.get(user.getID());
            changingUser.setName(newName);
            log.info("Имя успешно изменено!");
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * Изменение пользовательской фамилии
     *
     * @param user       пользователь, у которого необходимо изменить фамилию
     * @param newSurName новая фамилия
     */
    protected void changeUserSurname(User user, String newSurName) {
        try {
            checkUsersAvailability(user);
            User changingUser = users.get(user.getID());
            changingUser.setSurname(newSurName);
            log.info("Фамилия успешно изменена!");
        } catch (UserNotFoundException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * Получение списка всех пользователей
     */
    protected void getAllUsers() {
        List<User> userList = new ArrayList<>();
        for (Map.Entry<String, User> entry : users.entrySet()) {
            userList.add(entry.getValue());
        }
        log.info(userList.toString());
    }
}
