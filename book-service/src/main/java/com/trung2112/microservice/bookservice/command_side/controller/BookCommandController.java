package com.trung2112.microservice.bookservice.command_side.controller;

import com.trung2112.microservice.bookservice.command_side.command.CreateBookCommand;
import com.trung2112.microservice.bookservice.command_side.command.DeleteBookCommand;
import com.trung2112.microservice.bookservice.command_side.command.UpdateBookCommand;
import com.trung2112.microservice.bookservice.command_side.dto.BookRequestDto;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
public class BookCommandController {
    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    public String addBook(@RequestBody BookRequestDto bookRequestDto) {
        System.out.println("BookCommandController-addBook");
        CreateBookCommand command =
                new CreateBookCommand(
                        UUID.randomUUID().toString(),
                        bookRequestDto.getName(),
                        bookRequestDto.getAuthor(),
                        true
                );
        commandGateway.sendAndWait(command);
        return "added Book";
    }

    @PutMapping
    public String updateBook(@RequestBody BookRequestDto bookRequestDto) {
        UpdateBookCommand command =
                new UpdateBookCommand(
                        bookRequestDto.getBookId(),
                        bookRequestDto.getName(),
                        bookRequestDto.getAuthor(),
                        bookRequestDto.getIsReady()
                );
        commandGateway.sendAndWait(command);
        return "updated book";
    }

    @DeleteMapping("/{bookId}")
    public String deleteBook(@PathVariable String bookId) {
        commandGateway.sendAndWait(new DeleteBookCommand(bookId));
        return "deleted book";
    }
}
