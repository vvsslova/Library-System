package com.github.vvsslova;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс описывает взаимодействие с книгами
 */
@Slf4j
@Data
public class BookControl {
    private final Map<String, Book> books;

    public BookControl() {
        this.books = new HashMap<>();
    }

    /**
     * Добавление книги
     *
     * @param book добавляемая книга
     */
    protected void addBook(Book book) {
        try {
            checkBookAbsence(book);
            books.put(book.getID(), book);
            log.info("Книга {} успешно добавлена в библиотеку", book);
        } catch (BookAlreadyExistsException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * Проверка наличия книги
     *
     * @param book проверяемая книга
     * @throws BookAlreadyExistsException если такая книга уже существует
     */
    private void checkBookAbsence(Book book) throws BookAlreadyExistsException {
        if (books.containsKey(book.getID())) {
            throw new BookAlreadyExistsException();
        }
    }

    /**
     * Проверка отсутствия книги
     *
     * @param book проверяемая книга
     * @throws BookNotFoundException если книга не найдена
     */
    private void checkBooksAvailability(Book book) throws BookNotFoundException {
        if (!books.containsKey(book.getID())) {
            throw new BookNotFoundException();
        }
    }

    /**
     * Удаление книги
     *
     * @param book удаляемая книга
     */
    protected void removeBook(Book book) {
        try {
            checkBooksAvailability(book);
            books.remove(book.getID());
            log.info("Книга {} успешно удалена из библиотеки", book);
        } catch (BookNotFoundException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * Изменение автора книги
     *
     * @param book      книга, у которой необходимо изменить
     * @param newAuthor новый автор
     */
    protected void changeBookAuthor(Book book, String newAuthor) {
        try {
            checkBooksAvailability(book);
            Book changingBook = books.get(book.getID());
            changingBook.setAuthor(newAuthor);
            log.info("Автор успешно изменён!");
        } catch (BookNotFoundException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * Изменение названия книги
     *
     * @param book     книга, у которой необходимо изменить
     * @param newTitle новое название
     */
    protected void changeBookTitle(Book book, String newTitle) {
        try {
            checkBooksAvailability(book);
            Book changingBook = books.get(book.getID());
            changingBook.setTitle(newTitle);
            log.info("Название успешно изменено!");
        } catch (BookNotFoundException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * Изменение жанра книги
     *
     * @param book         книга, у которой необходимо изменить
     * @param newBookGenre новый жанр
     */
    protected void changeBookGenre(Book book, BookGenre newBookGenre) {
        try {
            checkBooksAvailability(book);
            Book changingBook = books.get(book.getID());
            changingBook.setBookGenre(newBookGenre);
            log.info("Жанр успешно изменён!");
        } catch (BookNotFoundException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * Поиск книги
     *
     * @param title  название
     * @param author автор
     */
    protected void searchBooks(String title, String author) {
        List<Book> listOfBooks = new ArrayList<>(books.values());
        List<Book> bookList = listOfBooks.stream()
                .filter(b -> (title == null || b.getTitle().equalsIgnoreCase(title)) &&
                        (author == null || b.getAuthor().equalsIgnoreCase(author)))
                .collect(Collectors.toList());
        log.info(bookList.toString());
    }

    /**
     * Получение списка всех книг
     */
    protected void getAllBooks() {
        List<Book> bookList = new ArrayList<>();
        for (Map.Entry<String, Book> entry : books.entrySet()) {
            bookList.add(entry.getValue());
        }
        log.info(bookList.toString());
    }
}
