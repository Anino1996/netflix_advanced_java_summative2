package com.summative2.Summative2OforiAlbert.dao;

import com.summative2.Summative2OforiAlbert.models.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PublisherDaoImpl implements PublisherDao {

    public static final String GET_PUBLISHER_SQL = "select * from publisher where publisher_id = ?";


    public static final String GET_ALL_PUBLISHERS_SQL = "select * from publisher";

    public static final String ADD_PUBLISHER_SQL =
            "insert into publisher (name, street, city, state, postal_code, phone, email) values (?, ?, ?, ?, ?, ?, ?)";


    public static final String UPDATE_PUBLISHER_SQL =
            "update publisher set name = ?, street = ?, city = ?, state = ?, postal_code = ?, phone = ?, email = ? where publisher_id = ?";


    public static final String DELETE_PUBLISHER_SQL = "delete from publisher where publisher_id = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PublisherDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Publisher addPublisher(Publisher newPublisher) {
        jdbcTemplate.update(ADD_PUBLISHER_SQL,
                newPublisher.getName(),
                newPublisher.getStreet(),
                newPublisher.getCity(),
                newPublisher.getState(),
                newPublisher.getPostalCode(),
                newPublisher.getPhone(),
                newPublisher.getEmail());

        int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);

        newPublisher.setPublisherId(id);
        return newPublisher;
    }

    @Override
    public List<Publisher> getAllPublishers() {
        return jdbcTemplate.query(GET_ALL_PUBLISHERS_SQL, this::mapRowToPublisher);
    }

    @Override
    public Publisher getPublisher(int id) {
        try {
            return jdbcTemplate.queryForObject(GET_PUBLISHER_SQL, this::mapRowToPublisher, id);
        } catch (EmptyResultDataAccessException err) {
            return null;
        }
    }

    @Override
    public void deletePublisher(int id) {
        jdbcTemplate.update(DELETE_PUBLISHER_SQL, id);
    }

    @Override
    public void updatePublisher(Publisher newPublisher) {
        jdbcTemplate.update(UPDATE_PUBLISHER_SQL,
                newPublisher.getName(),
                newPublisher.getStreet(),
                newPublisher.getCity(),
                newPublisher.getState(),
                newPublisher.getPostalCode(),
                newPublisher.getPhone(),
                newPublisher.getEmail(),
                newPublisher.getPublisherId()
        );
    }


    private Publisher mapRowToPublisher(ResultSet result, int rowNum) throws SQLException {
        Publisher publisher = new Publisher();
        publisher.setPublisherId(result.getInt("publisher_id"));
        publisher.setCity(result.getString("city"));
        publisher.setEmail(result.getString("email"));
        publisher.setPostalCode(result.getString("postal_code"));
        publisher.setPhone(result.getString("phone"));
        publisher.setState(result.getString("state"));
        publisher.setStreet(result.getString("street"));
        publisher.setName(result.getString("name"));

        return publisher;
    }
}
