package com.summative2.Summative2OforiAlbert.dao;

import com.summative2.Summative2OforiAlbert.models.Book;

import java.util.List;

public interface BookDao {
    public Book addBook(Book newBook);

    public List<Book> getAllBooks();

    public Book getBook(int id);

    public List<Book> getBooksByAuthor(int authorId);

    public void deleteBook(int id);

    public void updateBook(Book newBook);

}
