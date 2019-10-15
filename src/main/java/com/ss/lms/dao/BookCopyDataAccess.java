package com.ss.lms.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.ss.lms.entity.BookCopy;
import com.ss.lms.entity.BookCopyId;

@Component
public interface BookCopyDataAccess extends CrudRepository<BookCopy, BookCopyId> {

}
