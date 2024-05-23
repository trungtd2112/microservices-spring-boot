package com.trung2112.microservice.bookservice.command_side.event_handler;

import com.trung2112.microservice.bookservice.command_side.entity.Book;
import com.trung2112.microservice.bookservice.command_side.event.BookCreatedEvent;
import com.trung2112.microservice.bookservice.command_side.event.BookDeletedEvent;
import com.trung2112.microservice.bookservice.command_side.event.BookUpdatedEvent;
import com.trung2112.microservice.bookservice.command_side.repository.BookRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookEventsHandler {
    @Autowired
    private BookRepository bookRepository;

    @EventHandler
    public void on(BookCreatedEvent event) {
        System.out.println("BookEventsHandler-on-BookCreatedEvent");
        Book book = new Book();
        BeanUtils.copyProperties(event,book);
        bookRepository.save(book);
    }

    @EventHandler
    public void on(BookUpdatedEvent event) {
        Book book = bookRepository.getReferenceById(event.getBookId());
        book.setAuthor(event.getAuthor());
        book.setName(event.getName());
        book.setIsReady(event.getIsReady());
        bookRepository.save(book);
    }
    @EventHandler
    public void on(BookDeletedEvent event) {
        bookRepository.deleteById(event.getBookId());
    }
}
