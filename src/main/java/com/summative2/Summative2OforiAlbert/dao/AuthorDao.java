package com.summative2.Summative2OforiAlbert.dao;

import com.summative2.Summative2OforiAlbert.models.Author;

import java.util.List;

public interface AuthorDao {

    public Author addAuthor(Author newAuthor);

    public List<Author> getAllAuthors();

    public Author getAuthor(int id);

    public void deleteAuthor(int id);

    public void updateAuthor(Author newAuthor);

}
