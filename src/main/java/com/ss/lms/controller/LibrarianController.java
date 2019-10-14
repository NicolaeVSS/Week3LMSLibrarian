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
	}

	// Gets id from URI params
	// Reading a book copy by its bookId and its branchId
	@GetMapping(path = "/bookcopy/book/{bookId}/branch/{branchId}")
	public ResponseEntity<Optional<BookCopy>> readBookCopyById(@PathVariable Integer bookId,
			@PathVariable Integer branchId) {
		if (!userLibrarian.readBookById(bookId).isPresent()
				|| !userLibrarian.readLibraryBranchById(branchId).isPresent()) {
			//Code 404 if the records are not found in table
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Optional<BookCopy> bookCopy = userLibrarian.readBookCopyById(bookId, branchId);
		if(!bookCopy.isPresent()) {
			//if the bookCopy isnt found returns status code 404
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		//Returns 200 if is passes constraints
		return new ResponseEntity<Optional<BookCopy>>(bookCopy, HttpStatus.OK);
	}

	//Put request for updating a library branch
	//Gets the ids both from the json object and the URI
	//returns the corresponding httpstatus
	@PutMapping(path = "/branch/{branchId}")
	public ResponseEntity<HttpStatus> updateLibraryBranch(@RequestBody LibraryBranch libraryBranch) {
		if(libraryBranch.getBranchAddress() == null || libraryBranch.getBranchName() == null
				|| "".equals(libraryBranch.getBranchName()) || "".equals(libraryBranch.getBranchAddress())){
			//Code 400 for invalid request(nulls or blanks found)
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
		if (!userLibrarian.readLibraryBranchById(libraryBranch.getBranchId()).isPresent()) {
			//Code 404 if the branch isnt found in the table
			return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
		}
		//Updates and returns status code 200
		userLibrarian.updateLibraryBranch(libraryBranch);

		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	
	//Put request for updating a book copy
	//Gets the ids both from the json object and the URI
	//returns the corresponding httpstatus
	@PutMapping(path = "/bookCopy/book/{bookId}/branch/{branchId}")
	public ResponseEntity<HttpStatus> updateBookCopy(@RequestBody BookCopy bookCopy) {
		if(bookCopy.getNoOfCopies() == null || bookCopy.getBookCopyId() == null
				|| bookCopy.getBookCopyId().getBookId() == null || bookCopy.getBookCopyId().getBranchId() == null) {
			//If any nulls are found returns a bad request status code
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
		if (!userLibrarian.readLibraryBranchById(bookCopy.getBookCopyId().getBranchId()).isPresent()
				|| !userLibrarian.readBookById(bookCopy.getBookCopyId().getBookId()).isPresent()) {
			//If either of the branch or book 
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
		if (userLibrarian.readBookCopyById(bookCopy.getBookCopyId().getBookId(), bookCopy.getBookCopyId().getBranchId())
				.isPresent()) {
			userLibrarian.createBookCopy(bookCopy);
			//If the bookcopy id was found in the existing tables, it will update the existing record
			//and send status code 200
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		// Status code 204
	}

	//Makes a delete request
	//Gets params from URI
	//Doesn't need to null check id's because URI must include them or 500 error
	@DeleteMapping(value = "/bookCopy/book/{bookId}/branch/{branchId}")
	public ResponseEntity<HttpStatus> deleteBookCopy(@PathVariable Integer bookId, @PathVariable Integer branchId) {
		if(!userLibrarian.readBookCopyById(bookId, branchId).isPresent()) {
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
		userLibrarian.deleteBookCopy(bookId, branchId);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	
}
