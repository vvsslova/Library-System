package com.github.vvsslova;

import lombok.Data;

import lombok.extern.slf4j.Slf4j;

/**
 * Класс проектирует систему библиотеки
 */
@Data
@Slf4j
public class Library {
    private final String name;
    private final UserControl userControl;
    private final BookControl bookControl;
    private final BookLending bookLending;

    public Library(String name) {
        this.name = name;
        this.userControl = new UserControl();
        this.bookControl = new BookControl();
        this.bookLending = new BookLending();
    }

    /**
     * Добавление книги
     *
     * @param book добавляемая книга
     */
    public void addBook(Book book) {
        bookControl.addBook(book);
    }

    /**
     * Удаление книги
     *
     * @param book удаляемая книга
     */
    public void removeBook(Book book) {
        bookControl.removeBook(book);
    }

    /**
     * Изменение автора
     *
     * @param book      книга, у которой необходимо изменить автора
     * @param newAuthor новый автор
     */
    public void changeBookAuthor(Book book, String newAuthor) {
        bookControl.changeBookAuthor(book, newAuthor);
    }

    /**
     * Изменение названия книги
     *
     * @param book     книга, у которой необходимо изменить название
     * @param newTitle новое название
     */
    public void changeBookTitle(Book book, String newTitle) {
        bookControl.changeBookTitle(book, newTitle);
    }

    /**
     * Изменение жанра
     *
     * @param book         книга, у которой необходимо изменить жанр
     * @param newBookGenre новый жанр
     */
    public void changeBookGenre(Book book, BookGenre newBookGenre) {
        bookControl.changeBookGenre(book, newBookGenre);
    }

    /**
     * Поиск книги
     *
     * @param title  название книги
     * @param author автор книги
     */
    public void searchBooks(String title, String author) {
        bookControl.searchBooks(title, author);
    }

    /**
     * Получение списка всех книг
     */
    public void getAllBooks() {
        bookControl.getAllBooks();
    }

    /**
     * Добавление пользователя
     *
     * @param user добавляемый пользователь
     */
    public void addUser(User user) {
        userControl.addUser(user);
    }

    /**
     * Удаление пользователя
     *
     * @param user удаляемый пользователь
     */
    public void removeUser(User user) {
        userControl.removeUser(user);
    }

    /**
     * Выдача книги
     *
     * @param book   выдаваемая книга
     * @param user   получающий пользователь
     * @param period период
     */
    public void lendBook(Book book, User user, int period) {
        bookLending.lendBook(book, user, period);
    }

    /**
     * Возврат книги
     *
     * @param book возвращемая книга
     * @param user возвращающий пользователь
     */
    public void returnBook(Book book, User user) {
        bookLending.returnBook(book, user);
    }

    /**
     * Изменение номера телефона пользователя
     *
     * @param user     пользователь, у которого необходимо изменить номер
     * @param newPhone новый номер
     */
    public void changeUserPhone(User user, long newPhone) {
        userControl.changeUserPhone(user, newPhone);
    }

    /**
     * Изменение имени пользователя
     *
     * @param user    пользователь, у которого необходимо изменить имя
     * @param newName новое имя
     */
    public void changeUserName(User user, String newName) {
        userControl.changeUserName(user, newName);
    }

    /**
     * Изменение фамилии пользователя
     *
     * @param user       пользователь, у которого необходимо изменить фамилию
     * @param newSurName новая фамилия
     */
    public void changeUserSurname(User user, String newSurName) {
        userControl.changeUserSurname(user, newSurName);
    }

    /**
     * Получение списка всех пользователей
     */
    public void getAllUsers() {
        userControl.getAllUsers();
    }
}
