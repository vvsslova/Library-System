package com.github.vvsslova;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс описывает выдачу книги пользователю
 */
@Slf4j
@Data
public class BookLending {
    private final Map<Book, LocalDate> lendingBooks = new HashMap<>();
    private final Map<String, Book> lendBookUsers = new HashMap<>();
    private final BookControl bookControl;

    public BookLending() {
        this.bookControl = new BookControl();
    }

    /**
     * Проверка выдачи книги
     *
     * @param book проверяемая книга
     * @throws BookAlreadyLendException если книга уже выдана
     */
    private void checkBookLending(Book book) throws BookAlreadyLendException {
        if (lendingBooks.containsKey(book)) {
            throw new BookAlreadyLendException();
        }
    }

    /**
     * Выдача книги
     *
     * @param book   выдаваемая книга
     * @param user   получающий пользователь
     * @param period период
     */
    protected void lendBook(Book book, User user, int period) {
        try {
            checkBookLending(book);
            LocalDate returnDate = LocalDate.now().plusDays(period);
            lendingBooks.put(book, returnDate);
            lendBookUsers.put(user.getID(), book);
            log.info("Книга {} выдана пользователю", book.getTitle());
        } catch (BookAlreadyLendException e1) {
            log.info(e1.getMessage());
        }
    }

    /**
     * Возврат книги
     *
     * @param book возвращаемая книга
     * @param user возвращающий пользователь
     */
    protected void returnBook(Book book, User user) {
        checkLendingPeriodDates(book);
        lendingBooks.remove(book);
        lendBookUsers.remove(user.getID());
        log.info("Книга {} возвращена в библотеку", book.getTitle());
    }

    /**
     * Проверка сроков возврата
     *
     * @param book книга, у которой проверяется срок возврата
     */
    private void checkLendingPeriodDates(Book book) {
        LocalDate today = LocalDate.now();
        for (Map.Entry<Book, LocalDate> entry : lendingBooks.entrySet()) {
            if (today.isAfter(entry.getValue())) {
                log.info("Книга {} просрочена пользователем !", entry.getKey().getTitle());
            } else {
                log.info("Книга {} возвращена в срок", entry.getKey().getTitle());
            }
        }
    }
}
