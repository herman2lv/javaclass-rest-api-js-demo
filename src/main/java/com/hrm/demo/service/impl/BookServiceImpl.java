package com.hrm.demo.service.impl;

import com.hrm.demo.data.entity.Book;
import com.hrm.demo.data.repository.BookRepository;
import com.hrm.demo.service.BookService;
import com.hrm.demo.service.dto.BookDto;
import com.hrm.demo.service.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final ModelMapper mapper;

    @Override
    public BookDto get(Long id) {
        return bookRepository.findById(id)
                .map(e -> mapper.map(e, BookDto.class))
                .orElseThrow(() -> new NotFoundException("Book, id:" + id));
    }

    @Override
    public Page<BookDto> getAll(Pageable pageable) {
        return bookRepository.findAll(pageable).map(e -> mapper.map(e, BookDto.class));
    }

    @Override
    public BookDto create(BookDto book) {
        Book entity = mapper.map(book, Book.class);
        entity = bookRepository.save(entity);
        return mapper.map(entity, BookDto.class);
    }

    @Override
    public BookDto update(BookDto book) {
        Book entity = mapper.map(book, Book.class);
        entity = bookRepository.save(entity);
        return mapper.map(entity, BookDto.class);
    }

    @Override
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new NotFoundException("Book, id:" + id);
        }
        bookRepository.deleteById(id);
    }

}
