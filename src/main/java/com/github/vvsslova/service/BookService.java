package com.github.vvsslova.service;

import com.github.vvsslova.ENUM.BookGenre;
import com.github.vvsslova.dto.BookDto;
import com.github.vvsslova.exception.BookAlreadyExistsException;
import com.github.vvsslova.exception.BookNotFoundException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.awt.print.Book;
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
     * @param ID ID проверямой книги
     * @throws BookAlreadyExistsException если такая книга уже существует
     */
    private void checkBookAbsence(String ID) throws BookAlreadyExistsException {
        if (books.containsKey(ID)) {
            throw new BookAlreadyExistsException();
        }
    }

    /**
     * Проверка отсутствия книги
     *
     * @param ID ID проверямой книги
     * @throws BookNotFoundException если книга не найдена
     */
    private void checkBooksAvailability(String ID) throws BookNotFoundException {
        if (!books.containsKey(ID)) {
            throw new BookNotFoundException();
        }
    }

    /**
     * Удаление книги
     *
     * @param bookDto удаляемая книга
     */
    protected void removeBook(BookDto bookDto) {
        try {
            checkBooksAvailability(bookDto.getID());
            books.remove(bookDto.getID());
            log.info("Книга {} успешно удалена из библиотеки", bookDto);
        } catch (BookNotFoundException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * Изменение автора книги
     *
     * @param ID        ID книги, у которой необходимо изменить
     * @param newAuthor новый автор
     */
    protected void changeBookAuthor(String ID, String newAuthor) {
        try {
            checkBooksAvailability(ID);
            BookDto changingBookDto = books.get(ID);
            changingBookDto.setAuthor(newAuthor);
            log.info("Автор успешно изменён!");
        } catch (BookNotFoundException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * Изменение названия книги
     *
     * @param ID       ID книги, у которой необходимо изменить
     * @param newTitle новое название
     */
    protected void changeBookTitle(String ID, String newTitle) {
        try {
            checkBooksAvailability(ID);
            BookDto changingBookDto = books.get(ID);
            changingBookDto.setTitle(newTitle);
            log.info("Название успешно изменено!");
        } catch (BookNotFoundException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * Изменение жанра книги
     *
     * @param ID           ID книги, у которой необходимо изменить
     * @param newBookGenre новый жанр
     */
    protected void changeBookGenre(String ID, BookGenre newBookGenre) {
        try {
            checkBooksAvailability(ID);
            BookDto changingBookDto = books.get(ID);
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
