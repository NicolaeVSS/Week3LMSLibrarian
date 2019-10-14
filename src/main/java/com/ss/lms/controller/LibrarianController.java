package com.ss.lms.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.lms.entity.Book;
import com.ss.lms.entity.BookCopy;
import com.ss.lms.entity.LibraryBranch;
import com.ss.lms.service.UserLibrarian;

@RestController
@RequestMapping(path = "/lms/librarian")
public class LibrarianController {

	// carry it out.
	@Autowired
	UserLibrarian userLibrarian;

	@PostMapping(value = "/bookcopy/")
	public ResponseEntity<HttpStatus> createBookCopy(@RequestBody BookCopy bookCopy) {
		if (userLibrarian.readBookCopyById(bookCopy.getBookCopyId().getBookId(), bookCopy.getBookCopyId().getBranchId())
				.isPresent()) {
			return new ResponseEntity<HttpStatus>(HttpStatus.CONFLICT);
		}
		if (!userLibrarian.readLibraryBranchById(bookCopy.getBookCopyId().getBranchId()).isPresent()
				|| !userLibrarian.readBookById(bookCopy.getBookCopyId().getBookId()).isPresent()) {
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
		userLibrarian.createBookCopy(bookCopy);
		return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
		// Code 201
	}

	@GetMapping(value = "/books/")
	public ResponseEntity<Iterable<Book>> readAllBooks() {
		Iterable<Book> books = userLibrarian.readAllBooks();
		if (!books.iterator().hasNext()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Iterable<Book>>(books, HttpStatus.OK);
		// return userLibrarian.readAllBooks();
	}

	// Reading a single book by its id
	@GetMapping(path = "/books/{bookId}")
	public ResponseEntity<Optional<Book>> readBookById(@PathVariable Integer bookId) {
		Optional<Book> book = userLibrarian.readBookById(bookId);
		if (!book.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Optional<Book>>(book, HttpStatus.OK);
	}

	// Reading all the libraryBranches in the table
	@GetMapping(path = "/branch/")
	public ResponseEntity<Iterable<LibraryBranch>> readAllLibraryBranches() {
		Iterable<LibraryBranch> branches = userLibrarian.readAllLibraryBranches();
		if (branches.iterator().hasNext()) {
			return new ResponseEntity<Iterable<LibraryBranch>>(branches, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	// Reading a single libraryBranch by its id
	// Returns a response entity with
	@GetMapping(value = "/branch/{branchId}")
	public ResponseEntity<Optional<LibraryBranch>> readLibraryBranchById(@PathVariable Integer branchId) {
		System.out.println("Hello.");
		Optional<LibraryBranch> libraryBranch = userLibrarian.readLibraryBranchById(branchId);
		if (!libraryBranch.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Optional<LibraryBranch>>(libraryBranch, HttpStatus.OK);
//		return userLibrarian.readLibraryBranchById(branchId);
	}

	// Reading a book copy by its bookId and its branchId
	@GetMapping(path = "/bookcopy/book/{bookId}/branch/{branchId}")
	public ResponseEntity<Optional<BookCopy>> readBookCopyById(@PathVariable Integer bookId,
			@PathVariable Integer branchId) {
		if (!userLibrarian.readBookById(bookId).isPresent()
				|| !userLibrarian.readLibraryBranchById(branchId).isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Optional<BookCopy> bookCopy = userLibrarian.readBookCopyById(bookId, branchId);
		if(!bookCopy.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Optional<BookCopy>>(bookCopy, HttpStatus.OK);
	}

	@PutMapping(path = "/branch/")
	public ResponseEntity<HttpStatus> updateLibraryBranch(@RequestBody LibraryBranch libraryBranch) {
		// System.out.println(libraryBranch.getBranchId()+ " " +
		// libraryBranch.getBranchName() + " " + libraryBranch.getBranchAddress());
		if (!userLibrarian.readLibraryBranchById(libraryBranch.getBranchId()).isPresent()) {
			return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
		}

		userLibrarian.updateLibraryBranch(libraryBranch);

		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	@PutMapping(path = "/bookCopy/")
	public ResponseEntity<HttpStatus> updateBookCopy(@RequestBody BookCopy bookCopy) {
		if (!userLibrarian.readLibraryBranchById(bookCopy.getBookCopyId().getBranchId()).isPresent()
				|| !userLibrarian.readBookById(bookCopy.getBookCopyId().getBookId()).isPresent()) {
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
		if (userLibrarian.readBookCopyById(bookCopy.getBookCopyId().getBookId(), bookCopy.getBookCopyId().getBranchId())
				.isPresent()) {
			userLibrarian.createBookCopy(bookCopy);
			return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		// Status code 204
	}

	@DeleteMapping(value = "/bookCopy/book/{bookId}/branch/{branchId}")
	public ResponseEntity<HttpStatus> deleteBookCopy(@PathVariable Integer bookId, @PathVariable Integer branchId) {
		if(!userLibrarian.readBookCopyById(bookId, branchId).isPresent()) {
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
		userLibrarian.deleteBookCopy(bookId, branchId);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	
}
