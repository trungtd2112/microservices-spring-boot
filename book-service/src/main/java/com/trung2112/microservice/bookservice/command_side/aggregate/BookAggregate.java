package com.trung2112.microservice.bookservice.command_side.aggregate;

import com.trung2112.microservice.bookservice.command_side.command.CreateBookCommand;
import com.trung2112.microservice.bookservice.command_side.command.DeleteBookCommand;
import com.trung2112.microservice.bookservice.command_side.command.UpdateBookCommand;
import com.trung2112.microservice.bookservice.command_side.event.BookCreatedEvent;
import com.trung2112.microservice.bookservice.command_side.event.BookDeletedEvent;
import com.trung2112.microservice.bookservice.command_side.event.BookUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class BookAggregate {
    @AggregateIdentifier
    private String bookId;
    private String name;
    private String author;
    private Boolean isReady;

    public BookAggregate() {

    }

    @CommandHandler
    public BookAggregate(CreateBookCommand createBookCommand) {
        System.out.println("BookAggregate-init");
        BookCreatedEvent bookCreatedEvent = new BookCreatedEvent();
        BeanUtils.copyProperties(createBookCommand, bookCreatedEvent);
        AggregateLifecycle.apply(bookCreatedEvent);
    }

    @EventSourcingHandler
    public void on(BookCreatedEvent event) {
        System.out.println("BookAggregate-on-BookCreatedEvent");
        this.bookId = event.getBookId();
        this.author = event.getAuthor();
        this.isReady = event.getIsReady();
        this.name = event.getName();
    }

    @CommandHandler
    public void handle(UpdateBookCommand updateBookCommand) {
        BookUpdatedEvent event = new BookUpdatedEvent();
        BeanUtils.copyProperties(updateBookCommand, event);

        AggregateLifecycle.apply(event);
    }
    @CommandHandler
    public void handle(DeleteBookCommand deleteBookCommand) {
        BookDeletedEvent event = new BookDeletedEvent();
        BeanUtils.copyProperties(deleteBookCommand, event);

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(BookUpdatedEvent event) {
        this.bookId = event.getBookId();
        this.author = event.getAuthor();
        this.name = event.getName();
        this.isReady = event.getIsReady();
    }
    @EventSourcingHandler
    public void on(BookDeletedEvent event) {
        this.bookId = event.getBookId();
    }
}
