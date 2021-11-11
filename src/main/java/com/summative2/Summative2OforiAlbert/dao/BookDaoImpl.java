package com.summative2.Summative2OforiAlbert.dao;

import com.summative2.Summative2OforiAlbert.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {

    public static final String GET_BOOK_SQL = "select * from book where book_id = ?";

    public static final String GET_BOOKS_BY_AUTHOR_SQL = "select * from book where author_id = ?";

    public static final String GET_ALL_BOOKS_SQL = "select * from book";

    public static final String ADD_BOOK_SQL =
            "insert into book (isbn, publish_date, author_id, title, publisher_id, price) values (?, ?, ?, ?, ?, ?)";

    public static final String UPDATE_BOOK_SQL =
            "update book set isbn = ?, publish_date = ?, author_id = ?, title = ?, publisher_id = ?, price = ? where book_id = ?";

    public static final String DELETE_BOOK_SQL = "delete from book where book_id = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Book addBook(Book newBook) {
        jdbcTemplate.update(ADD_BOOK_SQL,
                newBook.getIsbn(),
                newBook.getPublishDate(),
                newBook.getAuthorId(),
                newBook.getTitle(),
                newBook.getPublisherId(),
                newBook.getPrice());

        int bookId = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);
        newBook.setBookId(bookId);
        return newBook;
    }

    @Override
    public List<Book> getAllBooks() {
        return jdbcTemplate.query(GET_ALL_BOOKS_SQL, this::mapRowToBook);
    }

    @Override
    public Book getBook(int id) {
        try {
            return jdbcTemplate.queryForObject(GET_BOOK_SQL, this::mapRowToBook, id);
        } catch (EmptyResultDataAccessException err) {
            return null;
        }
    }

    @Override
    public List<Book> getBooksByAuthor(int authorId) {
        return jdbcTemplate.query(GET_BOOKS_BY_AUTHOR_SQL, this::mapRowToBook, authorId);
    }

    @Override
    public void deleteBook(int id) {
        jdbcTemplate.update(DELETE_BOOK_SQL, id);
    }

    @Override
    public void updateBook(Book newBook) {
        jdbcTemplate.update(UPDATE_BOOK_SQL,
                newBook.getIsbn(),
                newBook.getPublishDate(),
                newBook.getAuthorId(),
                newBook.getTitle(),
                newBook.getPublisherId(),
                newBook.getPrice(),
                newBook.getBookId());
    }


    private Book mapRowToBook(ResultSet result, int rowNum) throws SQLException {
        Book book = new Book();
        book.setBookId(result.getInt("book_id"));
        book.setIsbn(result.getString("isbn"));
        book.setAuthorId(result.getInt("author_id"));
        book.setPublisherId(result.getInt("publisher_id"));
        book.setTitle(result.getString("title"));
        book.setPublishDate(result.getDate("publish_date").toLocalDate());
        book.setPrice(result.getDouble("price"));

        return book;
    }
}
