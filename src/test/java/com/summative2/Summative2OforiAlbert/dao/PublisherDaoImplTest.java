package com.summative2.Summative2OforiAlbert.dao;

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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PublisherDaoImplTest {

    Publisher publisher1;
    Publisher publisher2;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    PublisherDao publisherDao;

    @Before
    public void setUp() throws Exception {
        List<Publisher> publishersToDelete = publisherDao.getAllPublishers();

        publishersToDelete.forEach(
                publisher -> publisherDao.deletePublisher(publisher.getPublisherId()));

        publisher1 = new Publisher();
        publisher1.setName("O'reilly");
        publisher1.setPostalCode("98109");
        publisher1.setStreet("Terry Ave");
        publisher1.setState("WA");
        publisher1.setPhone("800-673-4559");
        publisher1.setCity("Kent");
        publisher1.setEmail("oreilly@oreilly.com");

        publisher2 = new Publisher();
        publisher2.setName("O'reilly");
        publisher2.setPostalCode("09688");
        publisher2.setStreet("West ave");
        publisher2.setState("WA");
        publisher2.setPhone("800-998-9876");
        publisher2.setCity("Seattle");
        publisher2.setEmail("manning@mnnings.com");

    }

    @After
    public void tearDown(){
        List<Publisher> publishersToDelete = publisherDao.getAllPublishers();

        publishersToDelete.forEach(
                publisher -> publisherDao.deletePublisher(publisher.getPublisherId()));
    }

    @Test
    public void addedPublisherShouldExist() {
//        ARRANGE
        publisher1 = publisherDao.addPublisher(publisher1);

//        ACT
        Publisher addedPublisher = publisherDao.getPublisher(publisher1.getPublisherId());

//        ASSERT
        assertEquals(publisher1, addedPublisher, "Expected %s got %s".format(publisher1.toString(), addedPublisher.toString()));
    }

    @Test
    public void getAllPublishersShouldReturnPublishers() {
//        ARRANGE
        publisher1 = publisherDao.addPublisher(publisher1);
        publisher2 = publisherDao.addPublisher(publisher2);

//        ACT
        List<Publisher> publisherList = publisherDao.getAllPublishers();

//        ASSERT
        final int expectedCount = 2;
        assertEquals(expectedCount, publisherList.size(), "Expected %s publishers, got %s".format(String.valueOf(expectedCount), publisherList.size()));
    }


    @Test
    public void getPublisherShouldReturnPublisherById() {
//        ARRANGE
        publisher1 = publisherDao.addPublisher(publisher1);
        publisher2 = publisherDao.addPublisher(publisher2);

//        ACT
        Publisher queriedPublisher = publisherDao.getPublisher(publisher1.getPublisherId());

//        ASSERT
        assertEquals(publisher1, queriedPublisher, "Expected %s publishers, got %s".format(publisher1.toString(), queriedPublisher.toString()));
    }


    @Test
    public void deletePublisherShouldRemovePublisherFromTable() {
//        ARRANGE
        publisher1 = publisherDao.addPublisher(publisher1);
        publisher2 = publisherDao.addPublisher(publisher2);

//        ACT
        publisherDao.deletePublisher(publisher1.getPublisherId());

//        ASSERT
        assertNull(publisherDao.getPublisher(publisher1.getPublisherId()));
    }

    @Test
    public void updatePublisherShouldUpdatePublisherInTable() {
//        ARRANGE
        publisher1 = publisherDao.addPublisher(publisher1);
        publisher2 = publisherDao.addPublisher(publisher2);

//        ACT
        String newName = "New and improved";
        publisher1.setName(newName);
        publisherDao.updatePublisher(publisher1);

//        ASSERT
        Publisher updatedPublisher = publisherDao.getPublisher(publisher1.getPublisherId());
        assertEquals(newName, updatedPublisher.getName());
    }
}