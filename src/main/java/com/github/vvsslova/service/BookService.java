package com.github.vvsslova.service;

import com.github.vvsslova.constant.BookGenre;
import com.github.vvsslova.dto.BookDto;
import com.github.vvsslova.exception.BookAlreadyExistsException;
import com.github.vvsslova.exception.BookNotFoundException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Описание взаимодействия с книгами
 */
@Slf4j
@Data
public class BookService {
    private final Map<String, BookDto> books;

    public BookService() {
        this.books = new HashMap<>();
    }

    /**
     * Добавление книги
     *
     * @param bookDto добавляемая книга
     */
    protected void addBook(BookDto bookDto) {
        try {
            checkBookAbsence(bookDto.getID());
            books.put(bookDto.getID(), bookDto);
            log.info("Книга {} успешно добавлена в библиотеку", bookDto);
        } catch (BookAlreadyExistsException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * Проверка наличия книги
     *
     * @param bookID ID проверямой книги
     * @throws BookAlreadyExistsException если такая книга уже существует
     */
    private void checkBookAbsence(String bookID) throws BookAlreadyExistsException {
        if (books.containsKey(bookID)) {
            throw new BookAlreadyExistsException();
        }
    }

    /**
     * Проверка отсутствия книги
     *
     * @param bookID ID проверямой книги
     * @throws BookNotFoundException если книга не найдена
     */
    private void checkBooksAvailability(String bookID) throws BookNotFoundException {
        if (!books.containsKey(bookID)) {
            throw new BookNotFoundException();
        }
    }

    /**
     * Удаление книги
     *
     * @param bookID удаляемая книга
     */
    protected void removeBook(String bookID) {
        try {
            checkBooksAvailability(bookID);
            books.remove(bookID);
            log.info("Книга успешно удалена из библиотеки");
        } catch (BookNotFoundException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * Изменение автора книги
     *
     * @param bookID        ID книги, у которой необходимо изменить
     * @param newAuthor новый автор
     */
    protected void changeBookAuthor(String bookID, String newAuthor) {
        try {
            checkBooksAvailability(bookID);
            BookDto changingBookDto = books.get(bookID);
            changingBookDto.setAuthor(newAuthor);
            log.info("Автор успешно изменён!");
        } catch (BookNotFoundException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * Изменение названия книги
     *
     * @param bookID       ID книги, у которой необходимо изменить
     * @param newTitle новое название
     */
    protected void changeBookTitle(String bookID, String newTitle) {
        try {
            checkBooksAvailability(bookID);
            BookDto changingBookDto = books.get(bookID);
            changingBookDto.setTitle(newTitle);
            log.info("Название успешно изменено!");
        } catch (BookNotFoundException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * Изменение жанра книги
     *
     * @param bookID           ID книги, у которой необходимо изменить
     * @param newBookGenre новый жанр
     */
    protected void changeBookGenre(String bookID, BookGenre newBookGenre) {
        try {
            checkBooksAvailability(bookID);
            BookDto changingBookDto = books.get(bookID);
            changingBookDto.setBookGenre(newBookGenre);
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
    protected List<BookDto> searchBooks(String title, String author) {
        List<BookDto> listOfBookDtos = new ArrayList<>(books.values());
        List<BookDto> bookDtoList = listOfBookDtos.stream()
                .filter(b -> (title == null || b.getTitle().equalsIgnoreCase(title)) &&
                        (author == null || b.getAuthor().equalsIgnoreCase(author)))
                .collect(Collectors.toList());
        return bookDtoList;
    }

    /**
     * Получение списка всех книг
     */
    protected void printAllBooks() {
        List<BookDto> bookDtoList = new ArrayList<>();
        for (Map.Entry<String, BookDto> entry : books.entrySet()) {
            bookDtoList.add(entry.getValue());
        }
        log.info(bookDtoList.toString());
    }
}
