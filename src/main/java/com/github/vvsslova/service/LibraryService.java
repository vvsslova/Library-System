package com.github.vvsslova.service;

import com.github.vvsslova.constant.BookGenre;
import com.github.vvsslova.dto.BookDto;
import com.github.vvsslova.dto.UserDto;
import com.github.vvsslova.exception.BookAlreadyLendException;
import com.github.vvsslova.exception.UserLendBookException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.*;

/**
 * Класс проектирует систему библиотеки
 */
@Data
@Slf4j
public class LibraryService {
    private final String name;
    private final UserService userService;
    private final BookService bookService;
    private final List<Journal> lendingJournal;

    public LibraryService(String name) {
        this.name = name;
        this.userService = new UserService();
        this.bookService = new BookService();
        this.lendingJournal = new LinkedList<>();
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
     * @param bookID ID удаляемой книги
     */
    public void removeBook(String bookID) {
        try {
            checkBookLending(bookID);
            bookService.removeBook(bookID);
        } catch (BookAlreadyLendException e) {
            log.info("Эта книга выдана пользователям!");
            Iterator<Journal> journalIterator = lendingJournal.iterator();
            while (journalIterator.hasNext()) {
                Journal journal = journalIterator.next();
                if (journal.bookID.equals(bookID)) {
                    journalIterator.remove();
                    log.info("Книга {} по техническим причинам возвращена библиотекой!", journal.getBookTitle());
                }
            }
            bookService.removeBook(bookID);
        }
    }

    /**
     * Изменение автора
     *
     * @param bookID    ID изменяемой книги
     * @param newAuthor новый автор
     */
    public void changeBookAuthor(String bookID, String newAuthor) {
        bookService.changeBookAuthor(bookID, newAuthor);
    }

    /**
     * Изменение названия книги
     *
     * @param bookID   ID изменяемой книги
     * @param newTitle новое название
     */
    public void changeBookTitle(String bookID, String newTitle) {
        bookService.changeBookTitle(bookID, newTitle);
    }

    /**
     * Изменение жанра
     *
     * @param bookID       ID изменяемой книги
     * @param newBookGenre новый жанр
     */
    public void changeBookGenre(String bookID, BookGenre newBookGenre) {
        bookService.changeBookGenre(bookID, newBookGenre);
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
     * @param userID ID удаляемого пользователя
     */
    public void removeUser(String userID) {
        try {
            checkUserBookLending(userID);
            userService.removeUser(userID);
        } catch (UserLendBookException e) {
            log.info("Этот пользователь взял книги!");
            Iterator<Journal> journalIterator = lendingJournal.iterator();
            while (journalIterator.hasNext()) {
                Journal journal = journalIterator.next();
                if (journal.userID.equals(userID)) {
                    journalIterator.remove();
                    log.info("Книги пользователя по техническим причинам возвращены библиотекой!");
                }
            }
            userService.removeUser(userID);
        }
    }

    /**
     * Проверка выдачи книги
     *
     * @param bookID ID проверяемой книги
     * @throws BookAlreadyLendException если книга уже выдана
     */
    private void checkBookLending(String bookID) throws BookAlreadyLendException {
        for (Journal journalList : lendingJournal) {
            if (journalList.bookID.equals(bookID)) {
                throw new BookAlreadyLendException();
            }
        }
    }

    /**
     * Проверка наличия пользователя в журнале выдачи книг перед удалением
     *
     * @param userID ID проверяемого пользователя
     * @throws UserLendBookException в случае, если пользователь взял книги
     */
    private void checkUserBookLending(String userID) throws UserLendBookException {
        for (Journal journalList : lendingJournal) {
            if (journalList.userID.equals(userID)) {
                throw new UserLendBookException();
            }
        }
    }


    /**
     * Выдача книги
     *
     * @param bookID ID выдаваемой книги
     * @param userID ID получающего пользователя
     */
    public void lendBook(String bookID, String userID) {
        BookDto searchingBook = bookService.getBooks().get(bookID);
        List<BookDto> listLendingBook = bookService.searchBooks(searchingBook.getTitle(), searchingBook.getAuthor());
        BookDto lendingBook = listLendingBook.get(0);
        LocalDate returnDate = LocalDate.now().plusDays(14);
        lendingJournal.add(new Journal(bookID, userID, lendingBook.getTitle(), returnDate));
        log.info("Книга {} выдана пользователю", lendingBook.getTitle());
    }

    /**
     * Возврат книги
     *
     * @param bookID ID возвращаемой книги
     * @param userID ID возвращающего пользователя
     */
    public void returnBook(String bookID, String userID) {
        Iterator<Journal> journalIterator = lendingJournal.iterator();
        while (journalIterator.hasNext()) {
            Journal journalEntry = journalIterator.next();
            if (journalEntry.userID.equals(userID) &&
                    journalEntry.bookID.equals(bookID)) {
                checkLendingPeriodDates(bookID);
                journalIterator.remove();
                log.info("Книга {} возвращена в библотеку", journalEntry.getBookTitle());
            }
        }
    }

    /**
     * Проверка сроков возврата
     *
     * @param bookID ID проверяемой книги
     */
    private void checkLendingPeriodDates(String bookID) {
        LocalDate today = LocalDate.now();
        for (Journal journalList : lendingJournal) {
            if (journalList.bookID.equals(bookID) && journalList.returnDate.isBefore(today)) {
                log.info("Книга {} просрочена пользователем !", journalList.bookTitle);
            } else {
                log.info("Книга {} возвращена в срок", journalList.bookTitle);
            }
        }
    }

    /**
     * Изменение номера телефона пользователя
     *
     * @param userID   ID изменяемого пользователя
     * @param newPhone новый номер
     */
    public void changeUserPhone(String userID, long newPhone) {
        userService.changeUserPhone(userID, newPhone);
    }

    /**
     * Изменение имени пользователя
     *
     * @param userID  ID изменяемого пользователя
     * @param newName новое имя
     */
    public void changeUserName(String userID, String newName) {
        userService.changeUserName(userID, newName);
    }

    /**
     * Изменение фамилии пользователя
     *
     * @param userID     ID изменяемого пользователя
     * @param newSurName новая фамилия
     */
    public void changeUserSurname(String userID, String newSurName) {
        userService.changeUserSurname(userID, newSurName);
    }

    /**
     * Получение списка всех пользователей
     */
    public void printAllUsers() {
        userService.printAllUsers();
    }

    @Data
    private static class Journal {
        private final String userID;
        private final String bookTitle;
        private final LocalDate returnDate;
        private final String bookID;

        public Journal(String bookID, String userID, String bookTitle, LocalDate returnDate) {
            this.returnDate = returnDate;
            this.userID = userID;
            this.bookTitle = bookTitle;
            this.bookID = bookID;
        }
    }
}
