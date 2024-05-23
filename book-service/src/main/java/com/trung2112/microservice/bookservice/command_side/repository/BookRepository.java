package com.trung2112.microservice.bookservice.command_side.repository;

import com.trung2112.microservice.bookservice.command_side.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {

}
