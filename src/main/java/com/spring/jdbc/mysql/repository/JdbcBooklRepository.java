package com.spring.jdbc.mysql.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.spring.jdbc.mysql.model.Book;

@Repository
public class JdbcBooklRepository implements BookRepository {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public int save(Book tutorial) {
    return jdbcTemplate.update("INSERT INTO books (title, description, published) VALUES(?,?,?)",
        new Object[] { tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished() });
  }

  @Override
  public int update(Book tutorial) {
    return jdbcTemplate.update("UPDATE books SET title=?, description=?, published=? WHERE id=?",
        new Object[] { tutorial.getTitle(), tutorial.getDescription(), tutorial.isPublished(), tutorial.getId() });
  }

  @Override
  public Book findById(Long id) {
    try {
      Book tutorial = jdbcTemplate.queryForObject("SELECT * FROM books WHERE id=?",
          BeanPropertyRowMapper.newInstance(Book.class), id);

      return tutorial;
    } catch (IncorrectResultSizeDataAccessException e) {
      return null;
    }
  }

  @Override
  public int deleteById(Long id) {
    return jdbcTemplate.update("DELETE FROM books WHERE id=?", id);
  }

  @Override
  public List<Book> findAll() {
    return jdbcTemplate.query("SELECT * from books", BeanPropertyRowMapper.newInstance(Book.class));
  }

  @Override
  public List<Book> findByPublished(boolean published) {
    return jdbcTemplate.query("SELECT * from books WHERE published=?",
        BeanPropertyRowMapper.newInstance(Book.class), published);
  }

  @Override
  public List<Book> findByTitleContaining(String title) {
    String q = "SELECT * from books WHERE title LIKE '%" + title + "%'";

    return jdbcTemplate.query(q, BeanPropertyRowMapper.newInstance(Book.class));
  }

  @Override
  public int deleteAll() {
    return jdbcTemplate.update("DELETE from books");
  }
}
