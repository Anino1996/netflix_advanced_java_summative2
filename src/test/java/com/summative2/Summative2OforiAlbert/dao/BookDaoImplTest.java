package com.summative2.Summative2OforiAlbert.dao;

import com.summative2.Summative2OforiAlbert.models.Author;
import com.summative2.Summative2OforiAlbert.models.Book;
import com.summative2.Summative2OforiAlbert.models.Publisher;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BookDaoImplTest {

    Book book1;
    Book book2;
    Author author1;
    Author author2;
    Publisher publisher1;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    AuthorDao authorDao;

    @Autowired
    BookDao bookDao;

    @Autowired
    PublisherDao publisherDao;

    @Before
    public void setUp() throws Exception{
        List<Book> booksToDelete = bookDao.getAllBooks();

        booksToDelete.forEach(
                book -> bookDao.deleteBook(book.getBookId()));

        List<Author> authorsToDelete = authorDao.getAllAuthors();

        authorsToDelete.forEach(
                author -> authorDao.deleteAuthor(author.getAuthorId()));

        List<Publisher> publishersToDelete = publisherDao.getAllPublishers();

        publishersToDelete.forEach(
                publisher -> publisherDao.deletePublisher(publisher.getPublisherId()));

        author1 = new Author();
        author1.setFirstName("Stephen");
        author1.setLastName("King");
        author1.setStreet("Maine Street");
        author1.setState("ME");
        author1.setEmail("sKing@carrie.us");
        author1.setCity("Coon");
        author1.setPhone("746-839-3087");
        author1.setPostalCode("98101");

        author2 = new Author();
        author2.setFirstName("Enid");
        author2.setLastName("Blyton");
        author2.setStreet("Boren Ave");
        author2.setState("WA");
        author2.setEmail("eBlyton@roald.us");
        author2.setCity("Seattle");
        author2.setPhone("266-849-3097");
        author2.setPostalCode("98203");

        publisher1 = new Publisher();
        publisher1.setName("O'reilly");
        publisher1.setPostalCode("98109");
        publisher1.setStreet("Terry Ave");
        publisher1.setState("WA");
        publisher1.setPhone("800-673-4559");
        publisher1.setCity("Kent");
        publisher1.setEmail("oreilly@oreilly.com");

        book1 = new Book();
        book1.setPrice(6.99);
        book1.setTitle("Carrie");
        book1.setPublishDate(LocalDate.of(1996, 8, 12));
        book1.setIsbn("19829");

        book2 = new Book();
        book2.setPrice(4.99);
        book2.setTitle("The Magic Faraway Tree");
        book2.setPublishDate(LocalDate.of(1999, 1, 14));
        book2.setIsbn("32231");

        author1 = authorDao.addAuthor(author1);
        author2 = authorDao.addAuthor(author2);

        publisher1 = publisherDao.addPublisher(publisher1);

        book1.setAuthorId(author1.getAuthorId());
        book1.setPublisherId(publisher1.getPublisherId());

        book2.setAuthorId(author2.getAuthorId());
        book2.setPublisherId(publisher1.getPublisherId());
    }

    @After
    public void tearDown() {
        List<Book> booksToDelete = bookDao.getAllBooks();

        booksToDelete.forEach(
                book -> bookDao.deleteBook(book.getBookId()));

        List<Author> authorsToDelete = authorDao.getAllAuthors();

        authorsToDelete.forEach(
                author -> authorDao.deleteAuthor(author.getAuthorId()));

        List<Publisher> publishersToDelete = publisherDao.getAllPublishers();

        publishersToDelete.forEach(
                publisher -> publisherDao.deletePublisher(publisher.getPublisherId()));
    }

    @Test
    public void addedBookShouldExist() {
//        ARRANGE
        author1 = authorDao.addAuthor(author1);
        publisher1 = publisherDao.addPublisher(publisher1);

//        ACT
        Book addedBook = bookDao.addBook(book1);

//        ASSERT
        assertEquals(book1, addedBook, "Expected %s got %s".format(book1.toString(), addedBook.toString()));
    }

    @Test
    public void getAllBooksShouldReturnBooks() {
//        ARRANGE
        publisher1 = publisherDao.addPublisher(publisher1);
        author1 = authorDao.addAuthor(author1);
        author2 = authorDao.addAuthor(author2);

//        ACT
        book1 = bookDao.addBook(book1);
        book2 = bookDao.addBook(book2);

//        ASSERT
        List<Book> bookList = bookDao.getAllBooks();
        final int expectedCount = 2;
        assertEquals(expectedCount, bookList.size(), "Expected %s books, got %s".format(String.valueOf(expectedCount), bookList.size()));
    }


    @Test
    public void getBookShouldReturnBookById() {
//        ARRANGE
        publisher1 = publisherDao.addPublisher(publisher1);
        author1 = authorDao.addAuthor(author1);
        author2 = authorDao.addAuthor(author2);
        book1 = bookDao.addBook(book1);

//        ACT
        Book queriedBook = bookDao.getBook(book1.getBookId());

//        ASSERT
        assertEquals(book1, queriedBook, "Expected %s books, got %s".format(book1.toString(), queriedBook.toString()));
    }

    @Test
    public void getBooksByAuthorShouldReturnBookWithAuthorId() {
//        ARRANGE
        publisher1 = publisherDao.addPublisher(publisher1);
        author1 = authorDao.addAuthor(author1);
        author2 = authorDao.addAuthor(author2);
        book1 = bookDao.addBook(book1);

//        ACT
        List<Book> booksByAuthor1 = bookDao.getBooksByAuthor(author1.getAuthorId());

//        ASSERT
        for (Book book: booksByAuthor1) {
            assertEquals(author1.getAuthorId(), book.getAuthorId());
        }

//        TEARDOWN
        bookDao.deleteBook(book1.getBookId());
        authorDao.deleteAuthor(author1.getAuthorId());
        authorDao.deleteAuthor(author2.getAuthorId());
        publisherDao.deletePublisher(publisher1.getPublisherId());
    }

    @Test
    public void deleteBookShouldRemoveBookFromTable() {
//        ARRANGE
        publisher1 = publisherDao.addPublisher(publisher1);
        author1 = authorDao.addAuthor(author1);
        author2 = authorDao.addAuthor(author2);
        book1 = bookDao.addBook(book1);

//        ACT
        bookDao.deleteBook(book1.getBookId());

//        ASSERT
        assertNull(bookDao.getBook(book1.getBookId()));
    }

    @Test
    public void updateBookShouldUpdateBookInTable() {
//        ARRANGE
        publisher1 = publisherDao.addPublisher(publisher1);
        author1 = authorDao.addAuthor(author1);
        author2 = authorDao.addAuthor(author2);
        book1 = bookDao.addBook(book1);

        String newName = "New and improved";

        book1.setTitle(newName);

        bookDao.updateBook(book1);

        Book updatedBook = bookDao.getBook(book1.getBookId());

        assertEquals(newName, updatedBook.getTitle());
    }
}