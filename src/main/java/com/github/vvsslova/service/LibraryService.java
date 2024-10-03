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
import java.util.List;
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
        log.info(bookService.searchBooks(title, author).toString());
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
     * @param title  название выдаваемой книги
     * @param author автор выдаваемой книги
     * @param userID получающий пользователь
     * @param period период
     */
    public void lendBook(String title, String author, String userID, int period) {
        List<BookDto> listLendingBook = bookService.searchBooks(title, author);
        BookDto lendingBook = listLendingBook.get(0);
        try {
            checkBookLending(lendingBook.getID());
            LocalDate returnDate = LocalDate.now().plusDays(period);
            lendingJournal.put(userID, new Journal(lendingBook.getID(), returnDate));
            log.info("Книга {} выдана пользователю", lendingBook.getTitle());
        } catch (BookAlreadyLendException e1) {
            log.info(e1.getMessage());
        }
    }

    /**
     * Возврат книги
     *
     * @param title  название возвращаемой книги
     * @param userID возвращающий пользователь
     */
    public void returnBook(String title, String userID) {
        Journal lendingBookJournal = lendingJournal.get(userID);
        String IDLendingBook = lendingBookJournal.getBookID();
        checkLendingPeriodDates(IDLendingBook);
        lendingJournal.remove(IDLendingBook);
        log.info("Книга {} возвращена в библотеку", title);
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
        private final String bookID;
        private final LocalDate returnDate;

        public Journal(String bookID, LocalDate returnDate) {
            this.returnDate = returnDate;
            this.bookID = bookID;
        }
    }
}
