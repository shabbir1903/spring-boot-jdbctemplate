package com.spring.jdbc.mysql.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.jdbc.mysql.model.Book;
import com.spring.jdbc.mysql.repository.BookRepository;

//@CrossOrigin(origins = "http://localhost:8081")
//winget install --id Git.Git -e --source winget
@RestController
@RequestMapping("/api")
public class BookController {

  @Autowired
  BookRepository bookRepository;

  @GetMapping("/books")
  public ResponseEntity<List<Book>> getAllBooks(@RequestParam(required = false) String title) {
    try {
      List<Book> tutorials = new ArrayList<Book>();

      if (title == null)
    	  bookRepository.findAll().forEach(tutorials::add);
      else
    	  bookRepository.findByTitleContaining(title).forEach(tutorials::add);

      if (tutorials.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(tutorials, HttpStatus.OK);
    } catch (Exception exception) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/books/{id}")
  public ResponseEntity<Book> getBookById(@PathVariable("id") long id) {
    Book book = bookRepository.findById(id);

    if (book != null) {
      return new ResponseEntity<>(book, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/books")
  public ResponseEntity<String> createBook(@Valid @RequestBody Book tutorial) {
    try {
    	bookRepository.save(new Book(tutorial.getTitle(), tutorial.getDescription(), false));
      return new ResponseEntity<>("Books was created successfully.", HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/books/{id}")
  public ResponseEntity<String> updateBook(@PathVariable("id") long id, @RequestBody Book tutorial) {
    Book book = bookRepository.findById(id);

    if (book != null) {
    	book.setId(id);
    	book.setTitle(tutorial.getTitle());
    	book.setDescription(tutorial.getDescription());
    	book.setPublished(tutorial.isPublished());

      bookRepository.update(book);
      return new ResponseEntity<>("Book was updated successfully.", HttpStatus.OK);
    } else {
      return new ResponseEntity<>("Cannot find Books with id=" + id, HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/books/{id}")
  public ResponseEntity<String> deleteBook(@PathVariable("id") long id) {
    try {
      int result = bookRepository.deleteById(id);
      if (result == 0) {
        return new ResponseEntity<>("Cannot find Books with id=" + id, HttpStatus.OK);
      }
      return new ResponseEntity<>("Book was deleted successfully.", HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>("Cannot delete tutorial.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/books")
  public ResponseEntity<String> deleteAllBooks() {
    try {
      int numRows = bookRepository.deleteAll();
      return new ResponseEntity<>("Deleted " + numRows + " Book(s) successfully.", HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>("Cannot delete books.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  @GetMapping("/books/published")
  public ResponseEntity<List<Book>> findByPublished() {
    try {
      List<Book> tutorials = bookRepository.findByPublished(true);

      if (tutorials.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(tutorials, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
 

}
