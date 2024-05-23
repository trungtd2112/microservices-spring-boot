package com.trung2112.microservice.bookservice.query_side.controller;

import com.trung2112.microservice.bookservice.command_side.entity.Book;
import com.trung2112.microservice.bookservice.command_side.repository.BookRepository;
import com.trung2112.microservice.bookservice.query_side.dto.BookResponseDto;
import com.trung2112.microservice.bookservice.query_side.query.GetAllBooksQuery;
import com.trung2112.microservice.bookservice.query_side.query.GetBookQuery;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookQueryController {
    @Autowired
    private QueryGateway queryGateway;

    @GetMapping("/")
    public List<BookResponseDto> getAllBooks(){
        GetAllBooksQuery getAllBooksQuery = new GetAllBooksQuery();
        return queryGateway.query(
                        getAllBooksQuery, ResponseTypes.multipleInstancesOf(BookResponseDto.class))
                .join();
    }

    @GetMapping("/{bookId}")
    public BookResponseDto getBookDetail(@PathVariable String bookId) {
        System.out.println("BookQueryController-GetBook-" + bookId);
        GetBookQuery getBookQuery = new GetBookQuery();
        getBookQuery.setBookId(bookId);

        return queryGateway.query(getBookQuery, ResponseTypes.instanceOf(BookResponseDto.class)).join();
    }
}
