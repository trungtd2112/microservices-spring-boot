package com.trung2112.microservice.bookservice.query_side.projection;

import com.trung2112.microservice.bookservice.command_side.entity.Book;
import com.trung2112.microservice.bookservice.command_side.repository.BookRepository;
import com.trung2112.microservice.bookservice.query_side.dto.BookResponseDto;
import com.trung2112.microservice.bookservice.query_side.query.GetAllBooksQuery;
import com.trung2112.microservice.bookservice.query_side.query.GetBookQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookProjection {
    @Autowired
    private BookRepository bookRepository;

    @QueryHandler
    public BookResponseDto handle(GetBookQuery getBookQuery) {
        System.out.println("BookProjection-handle-getBook");
        BookResponseDto bookResponseDto = new BookResponseDto();
        Book book = bookRepository.getReferenceById(getBookQuery.getBookId());
        BeanUtils.copyProperties(book, bookResponseDto);
        return bookResponseDto;
    }

    @QueryHandler
    List<BookResponseDto> handle(GetAllBooksQuery getAllBooksQuery){
        List<Book> listEntity = bookRepository.findAll();
        List<BookResponseDto> listbook = new ArrayList<>();
        listEntity.forEach(s -> {
            BookResponseDto model = new BookResponseDto();
            BeanUtils.copyProperties(s, model);
            listbook.add(model);
        });
        return listbook;
    }
}
