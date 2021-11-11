package com.summative2.Summative2OforiAlbert.dao;

import com.summative2.Summative2OforiAlbert.models.Author;
import com.summative2.Summative2OforiAlbert.models.Book;
import com.summative2.Summative2OforiAlbert.models.Publisher;

import java.util.List;

public interface PublisherDao {

    public Publisher addPublisher(Publisher newPublisher);

    public List<Publisher> getAllPublishers();

    public Publisher getPublisher(int id);

    public void deletePublisher(int id);

    public void updatePublisher(Publisher newPublisher);
}
