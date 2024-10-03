package com.github.vvsslova.service;

import com.github.vvsslova.ENUM.BookGenre;
import com.github.vvsslova.dto.BookDto;
import com.github.vvsslova.dto.UserDto;
import com.github.vvsslova.exception.BookAlreadyLendException;
import lombok.Data;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс проектирует систему библиотеки
 */
@Data
@Slf4j
public class LibraryService {
    private final String name;
    private final UserService userService;
    private final BookService bookService;
    private final Map<String, Journal> lendingJournal;

    public LibraryService(String name) {
        this.name = name;
        this.userService = new UserService();
        this.bookService = new BookService();
        this.lendingJournal = new HashMap<>();
    }

    /**
     * Добавление книги
     *
     * @param bookDto добавляемая книга
     */
    public void addBook(BookDto bookDto) {
        bookService.addBook(bookDto);
    }

    /**
     * Удаление книги
     *
     * @param bookDto удаляемая книга
     */
    public void removeBook(BookDto bookDto) {
        bookService.removeBook(bookDto);
    }

    /**
     * Изменение автора
     *
     * @param bookDto   книга, у которой необходимо изменить автора
     * @param newAuthor новый автор
     */
    public void changeBookAuthor(BookDto bookDto, String newAuthor) {
        bookService.changeBookAuthor(bookDto.getID(), newAuthor);
    }

    /**
     * Изменение названия книги
     *
     * @param bookDto  книга, у которой необходимо изменить название
     * @param newTitle новое название
     */
    public void changeBookTitle(BookDto bookDto, String newTitle) {
        bookService.changeBookTitle(bookDto.getID(), newTitle);
    }

    /**
     * Изменение жанра
     *
     * @param bookDto      книга, у которой необходимо изменить жанр
     * @param newBookGenre новый жанр
     */
    public void changeBookGenre(BookDto bookDto, BookGenre newBookGenre) {
        bookService.changeBookGenre(bookDto.getID(), newBookGenre);
    }

    /**
     * Поиск книги
     *
     * @param title  название книги
     * @param author автор книги
     */
    public void printFoundBook(String title, String author) {
        bookService.searchBooks(title, author);
    }

    /**
     * Получение списка всех книг
     */
    public void printAllBooks() {
        bookService.printAllBooks();
    }

    /**
     * Добавление пользователя
     *
     * @param userDto добавляемый пользователь
     */
    public void addUser(UserDto userDto) {
        userService.addUser(userDto);
    }

    /**
     * Удаление пользователя
     *
     * @param userDto удаляемый пользователь
     */
    public void removeUser(UserDto userDto) {
        userService.removeUser(userDto);
    }

    /**
     * Проверка выдачи книги
     *
     * @param ID ID проверяемой книги
     * @throws BookAlreadyLendException если книга уже выдана
     */
    private void checkBookLending(String ID) throws BookAlreadyLendException {
        if (lendingJournal.containsKey(ID)) {
            throw new BookAlreadyLendException();
        }
    }


    /**
     * Выдача книги
     *
     * @param bookDto выдаваемая книга
     * @param userDto получающий пользователь
     * @param period  период
     */
    public void lendBook(BookDto bookDto, UserDto userDto, int period) {
        try {
            checkBookLending(bookDto.getID());
            LocalDate returnDate = LocalDate.now().plusDays(period);
            lendingJournal.put(bookDto.getID(), new Journal(userDto.getID(), returnDate));
            log.info("Книга {} выдана пользователю", bookDto.getTitle());
        } catch (BookAlreadyLendException e1) {
            log.info(e1.getMessage());
        }
    }

    /**
     * Возврат книги
     *
     * @param bookDto возвращаемая книга
     * @param userDto возвращающий пользователь
     */
    public void returnBook(BookDto bookDto, UserDto userDto) {
        checkLendingPeriodDates(bookDto.getID());
        lendingJournal.remove(bookDto.getID());
        log.info("Книга {} возвращена в библотеку", bookDto.getTitle());
    }

    /**
     * Проверка сроков возврата
     *
     * @param ID ID проверяемой книги
     */
    private void checkLendingPeriodDates(String ID) {
        LocalDate today = LocalDate.now();
        for (Map.Entry<String, Journal> entry : lendingJournal.entrySet()) {
            if (today.isAfter(entry.getValue().returnDate)) {
                log.info("Книга просрочена пользователем !");
            } else {
                log.info("Книга возвращена в срок");
            }
        }
    }

    /**
     * Изменение номера телефона пользователя
     *
     * @param userDto  пользователь, у которого необходимо изменить номер
     * @param newPhone новый номер
     */
    public void changeUserPhone(UserDto userDto, long newPhone) {
        userService.changeUserPhone(userDto.getID(), newPhone);
    }

    /**
     * Изменение имени пользователя
     *
     * @param userDto пользователь, у которого необходимо изменить имя
     * @param newName новое имя
     */
    public void changeUserName(UserDto userDto, String newName) {
        userService.changeUserName(userDto.getID(), newName);
    }

    /**
     * Изменение фамилии пользователя
     *
     * @param userDto    пользователь, у которого необходимо изменить фамилию
     * @param newSurName новая фамилия
     */
    public void changeUserSurname(UserDto userDto, String newSurName) {
        userService.changeUserSurname(userDto.getID(), newSurName);
    }

    /**
     * Получение списка всех пользователей
     */
    public void printAllUsers() {
        userService.printAllUsers();
    }

    @Getter
    private static class Journal {
        private final String userID;
        private final LocalDate returnDate;

        public Journal(String userID, LocalDate returnDate) {
            this.returnDate = returnDate;
            this.userID = userID;
        }
    }
}
