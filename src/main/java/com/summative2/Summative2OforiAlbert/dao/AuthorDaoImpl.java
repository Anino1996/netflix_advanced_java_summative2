package com.summative2.Summative2OforiAlbert.dao;

import com.summative2.Summative2OforiAlbert.models.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AuthorDaoImpl implements AuthorDao {

    public static final String GET_AUTHOR_SQL = "select * from author where author_id = ?";

    public static final String GET_ALL_AUTHORS_SQL = "select * from author";

    public static final String ADD_AUTHOR_SQL =
            "insert into author (first_name, last_name, street, city, state, postal_code, phone, email) values (?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String UPDATE_AUTHOR_SQL =
            "update author set first_name = ?, last_name = ?, street = ?, city = ?, state = ?, postal_code = ?, phone = ?, email = ? where author_id = ?";

    public static final String DELETE_AUTHOR_SQL = "delete from author where author_id = ?";


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public AuthorDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Author addAuthor(Author newAuthor) {
        jdbcTemplate.update(ADD_AUTHOR_SQL,
                newAuthor.getFirstName(),
                newAuthor.getLastName(),
                newAuthor.getStreet(),
                newAuthor.getCity(),
                newAuthor.getState(),
                newAuthor.getPostalCode(),
                newAuthor.getPhone(),
                newAuthor.getEmail());

        int id  = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);

        newAuthor.setAuthorId(id);
        return newAuthor;
    }

    @Override
    public List<Author> getAllAuthors() {
        return jdbcTemplate.query(GET_ALL_AUTHORS_SQL, this::mapRowToAuthor);
    }

    @Override
    public Author getAuthor(int id) {
        try{
            return jdbcTemplate.queryForObject(GET_AUTHOR_SQL, this::mapRowToAuthor, id);
        } catch (EmptyResultDataAccessException err) {
            return null;
        }
    }

    @Override
    public void deleteAuthor(int id) {
        jdbcTemplate.update(DELETE_AUTHOR_SQL, id);
    }

    @Override
    public void updateAuthor(Author newAuthor) {
        jdbcTemplate.update(UPDATE_AUTHOR_SQL,
                newAuthor.getFirstName(),
                newAuthor.getLastName(),
                newAuthor.getStreet(),
                newAuthor.getCity(),
                newAuthor.getState(),
                newAuthor.getPostalCode(),
                newAuthor.getPhone(),
                newAuthor.getEmail(),
                newAuthor.getAuthorId());
    }


    private Author mapRowToAuthor(ResultSet result, int rowNum) throws SQLException {
        Author author = new Author();
        author.setAuthorId(result.getInt("author_id"));
        author.setFirstName(result.getString("first_name"));
        author.setLastName(result.getString("last_name"));
        author.setPhone(result.getString("phone"));
        author.setCity(result.getString("city"));
        author.setPostalCode(result.getString("postal_code"));
        author.setEmail(result.getString("email"));
        author.setState(result.getString("state"));
        author.setStreet(result.getString("street"));

        return author;
    }
}
