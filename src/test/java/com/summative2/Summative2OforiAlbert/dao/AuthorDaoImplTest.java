package com.summative2.Summative2OforiAlbert.dao;

import com.summative2.Summative2OforiAlbert.models.Author;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AuthorDaoImplTest {

    Author author1;
    Author author2;

    @Autowired
    protected AuthorDao authorDao;

    @Before
    public void setUp() throws Exception {
        clearTable();

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
    }

    @After
    public void tearDown() {
        clearTable();
    }

    private void clearTable() {
        List<Author> authorsToDelete = authorDao.getAllAuthors();

        authorsToDelete.forEach(
                author -> authorDao.deleteAuthor(author.getAuthorId()));
    }

    @Test
    public void addedAuthorShouldExist() {
//        ARRANGE
        author1 = authorDao.addAuthor(author1);

//        ACT
        Author addedAuthor = authorDao.addAuthor(author1);

//        ASSERT
        assertEquals(author1, addedAuthor, "Expected %s got %s".format(author1.toString(), addedAuthor.toString()));
    }

    @Test
    public void getAllAuthorsShouldReturnAuthors() {
//        ARRANGE
        author1 = authorDao.addAuthor(author1);
        author2 = authorDao.addAuthor(author2);

//        ACT
        List<Author> authorList = authorDao.getAllAuthors();

//        ASSERT
        final int expectedCount = 2;
        assertEquals(expectedCount, authorList.size(), "Expected %s authors, got %s".format(String.valueOf(expectedCount), authorList.size()));
    }


    @Test
    public void getAuthorShouldReturnAuthorById() {
//        ARRANGE
        author1 = authorDao.addAuthor(author1);
        author2 = authorDao.addAuthor(author2);

//        ACT
        Author queriedAuthor = authorDao.getAuthor(author1.getAuthorId());

//        ASSERT
        assertEquals(author1, queriedAuthor, "Expected %s authors, got %s".format(author1.toString(), queriedAuthor.toString()));
    }


    @Test
    public void deleteAuthorShouldRemoveAuthorFromTable() {
//        ARRANGE
        author1 = authorDao.addAuthor(author1);
        author2 = authorDao.addAuthor(author2);

//        ACT
        authorDao.deleteAuthor(author1.getAuthorId());

//        ASSERT
        assertNull(authorDao.getAuthor(author1.getAuthorId()));
    }

    @Test
    public void updateAuthorShouldUpdateAuthorInTable() {
//        ARRANGE
        author1 = authorDao.addAuthor(author1);
        author2 = authorDao.addAuthor(author2);

//        ACT
        String newName = "New";
        author1.setFirstName(newName);
        authorDao.updateAuthor(author1);

//        ASSERT
        Author updatedAuthor = authorDao.getAuthor(author1.getAuthorId());
        assertEquals(newName, updatedAuthor.getFirstName());
    }
}