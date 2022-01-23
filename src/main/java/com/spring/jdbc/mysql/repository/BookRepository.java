package com.spring.jdbc.mysql.repository;

import java.util.List;

import com.spring.jdbc.mysql.model.Book;

public interface BookRepository {
  int save(Book book);

  int update(Book book);

  Book findById(Long id);

  int deleteById(Long id);

  List<Book> findAll();

  List<Book> findByPublished(boolean published);

  List<Book> findByTitleContaining(String title);

  int deleteAll();
}
