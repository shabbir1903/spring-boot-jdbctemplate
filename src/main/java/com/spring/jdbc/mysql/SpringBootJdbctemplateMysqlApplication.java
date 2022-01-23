package com.spring.jdbc.mysql;
/*
 * 
 * shabbir.ali1903@gmail.com
 * Shabbir Ali
 * 
 * CREATE TABLE books
(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255),
    description VARCHAR(255),
    published BOOLEAN
);
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootJdbctemplateMysqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJdbctemplateMysqlApplication.class, args);
	}

}
