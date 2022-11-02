package com.hrm.demo.service;

import com.hrm.demo.service.dto.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto get(Long id);

    Page<BookDto> getAll(Pageable pageable);

    BookDto create(BookDto book);

    BookDto update(BookDto book);

    void delete(Long id);
}
