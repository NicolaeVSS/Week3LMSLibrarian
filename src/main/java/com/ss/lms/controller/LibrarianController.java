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
import com.ss.lms.entity.id.BookCopyId;
import com.ss.lms.service.UserLibrarian;

@RestController
@RequestMapping(path = "/lms/librarian")
public class LibrarianController {

	// carry it out.
	@Autowired
	UserLibrarian userLibrarian;

	@PostMapping(value = "/bookcopy/",consumes = {"application/xml", "application/json"},
										produces = {"application/xml", "application/json"})
	public ResponseEntity<BookCopy> createBookCopy(@RequestBody BookCopy bookCopy) {
		if (userLibrarian.readBookCopyById(bookCopy.getBookCopyId().getBookId(), bookCopy.getBookCopyId().getBranchId())
				.isPresent()) {
			return new ResponseEntity<BookCopy>(new BookCopy(),HttpStatus.CONFLICT);
		}
		if (!userLibrarian.readLibraryBranchById(bookCopy.getBookCopyId().getBranchId()).isPresent()
				|| !userLibrarian.readBookById(bookCopy.getBookCopyId().getBookId()).isPresent()) {
			return new ResponseEntity<BookCopy>(new BookCopy(), HttpStatus.BAD_REQUEST);
		}
		userLibrarian.createBookCopy(bookCopy);
		return new ResponseEntity<BookCopy>(new BookCopy(), HttpStatus.CREATED);
		// Code 201
	}

	@GetMapping(value = "/book/",produces = {"application/xml", "application/json"})
	public ResponseEntity<Iterable<Book>> readAllBooks() {
		Iterable<Book> books = userLibrarian.readAllBooks();
		if (!books.iterator().hasNext()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Iterable<Book>>(books, HttpStatus.OK);
		// return userLibrarian.readAllBooks();
	}

	// Reading a single book by its id
	@GetMapping(path = "/book/{bookId}",produces = {"application/xml", "application/json"})
	public ResponseEntity<Optional<Book>> readBookById(@PathVariable Integer bookId) {
		Optional<Book> book = userLibrarian.readBookById(bookId);
		System.out.println(book.toString());
		if (!book.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Optional<Book>>(book, HttpStatus.OK);
	}

	// Reading all the libraryBranches in the table
	@GetMapping(path = "/branch/",produces = {"application/xml", "application/json"})
	public ResponseEntity<Iterable<LibraryBranch>> readAllLibraryBranches() {
		Iterable<LibraryBranch> branches = userLibrarian.readAllLibraryBranches();
		if (branches.iterator().hasNext()) {
			return new ResponseEntity<Iterable<LibraryBranch>>(branches, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	// Reading a single libraryBranch by its id
	// Returns a response entity with
	@GetMapping(value = "/branch/{branchId}",produces = {"application/xml", "application/json"})
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
	@GetMapping(path = "/bookcopy/book/{bookId}/branch/{branchId}",produces = {"application/xml", "application/json"})
	public ResponseEntity<Optional<BookCopy>> readBookCopyById(@PathVariable Integer bookId,
			@PathVariable Integer branchId) {
		System.out.println("Hello...");
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
	@PutMapping(path = "/branch/{branchId}",consumes = {"application/xml", "application/json"})
	public ResponseEntity<LibraryBranch> updateLibraryBranch(@PathVariable Integer branchId, @RequestBody LibraryBranch libraryBranch) {
		if(libraryBranch.getBranchId() != null){
			return new ResponseEntity<LibraryBranch>(new LibraryBranch(),HttpStatus.BAD_REQUEST);
		}
		libraryBranch.setBranchId(branchId);
		if(libraryBranch.getBranchAddress() == null 
				|| libraryBranch.getBranchName() == null
				|| "".equals(libraryBranch.getBranchName()) 
				|| "".equals(libraryBranch.getBranchAddress())){
			//Code 400 for invalid request(nulls or blanks found)
			return new ResponseEntity<LibraryBranch>(new LibraryBranch(),HttpStatus.BAD_REQUEST);
		}
		if (!userLibrarian.readLibraryBranchById(libraryBranch.getBranchId()).isPresent()) {
			//Code 404 if the branch isnt found in the table
			return new ResponseEntity<LibraryBranch>(new LibraryBranch(), HttpStatus.NOT_FOUND);
		}
		//Updates and returns status code 200
		userLibrarian.updateLibraryBranch(libraryBranch);

		return new ResponseEntity<LibraryBranch>(libraryBranch, HttpStatus.OK);
	}

	
	//Put request for updating a book copy
	//Gets the ids both from the json object and the URI
	//returns the corresponding httpstatus
	@PutMapping(path = "/bookcopy/book/{bookId}/branch/{branchId}")
	public ResponseEntity<BookCopy> updateBookCopy(@PathVariable Integer bookId,
													 @PathVariable Integer branchId,
													 @RequestBody BookCopy bookCopy) {
		if((bookCopy.getBookCopyId().getBookId() != null && bookCopy.getBookCopyId().getBranchId() != null)||
				bookCopy.getNoOfCopies() == null) {
			return new ResponseEntity<BookCopy>(new BookCopy(), HttpStatus.BAD_REQUEST);
		}
		bookCopy.setBookCopyId(new BookCopyId(bookId, branchId));
		if (!userLibrarian.readLibraryBranchById(branchId).isPresent()
				|| !userLibrarian.readBookById(bookId).isPresent()) {
			//If either of the branch or book 
			return new ResponseEntity<BookCopy>(new BookCopy(), HttpStatus.BAD_REQUEST);
		}
		if (userLibrarian.readBookCopyById(bookId, branchId).isPresent()) {
			userLibrarian.updateBookCopy(bookCopy);
			//If the bookcopy id was found in the existing tables, it will update the existing record
			//and send status code 200
			return new ResponseEntity<BookCopy>(bookCopy, HttpStatus.OK);
		}

		return new ResponseEntity<BookCopy>(new BookCopy(), HttpStatus.BAD_REQUEST);
		// Status code 204
	}

	//Makes a delete request
	//Gets params from URI
	//Doesn't need to null check id's because URI must include them or 500 error
	@DeleteMapping(value = "/bookcopy/book/{bookId}/branch/{branchId}")
	public ResponseEntity<HttpStatus> deleteBookCopy(@PathVariable Integer bookId, @PathVariable Integer branchId) {
		if(!userLibrarian.readBookCopyById(bookId, branchId).isPresent()) {
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
		userLibrarian.deleteBookCopy(bookId, branchId);
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}
	
	
}
