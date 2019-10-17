package com.ss.lms.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ss.lms.entity.Book;
import com.ss.lms.entity.BookCopy;
import com.ss.lms.entity.LibraryBranch;
import com.ss.lms.entity.BookCopyId;
import com.ss.lms.service.UserLibrarian;

@RestController
@RequestMapping(path = "/lms/librarian")
public class LibrarianController {

	// carry it out.
	@Autowired
	UserLibrarian userLibrarian;

	@PostMapping(value = "/bookcopy",consumes = {"application/xml", "application/json"},
										produces = {"application/xml", "application/json"})
	public ResponseEntity<BookCopy> createBookCopy(@RequestBody BookCopy bookCopy) {
		if (userLibrarian.readBookCopyById(new BookCopyId(bookCopy.getBookId(), bookCopy.getBranchId()))
				.isPresent()) {
			return new ResponseEntity<BookCopy>(new BookCopy(),HttpStatus.CONFLICT);
		}
		if (!userLibrarian.readLibraryBranchById(bookCopy.getBranchId()).isPresent()
				|| !userLibrarian.readBookById(bookCopy.getBookId()).isPresent()) {
			return new ResponseEntity<BookCopy>(new BookCopy(), HttpStatus.BAD_REQUEST);
		}
		userLibrarian.createBookCopy(bookCopy);
		return new ResponseEntity<BookCopy>(bookCopy, HttpStatus.CREATED);
		// Code 201
	}

	@GetMapping(value = "/books",produces = {"application/xml", "application/json"})
	public ResponseEntity<Iterable<Book>> readAllBooks() {
		Iterable<Book> books = userLibrarian.readAllBooks();
		if (!books.iterator().hasNext()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Iterable<Book>>(books, HttpStatus.OK);
		// return userLibrarian.readAllBooks();
	}

	// Reading a single book by its id
	@GetMapping(path = "/book/{bookId}",produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<Book> readBookById(@PathVariable Integer bookId, @RequestHeader MultiValueMap<String, String> header) {
		Optional<Book> book = userLibrarian.readBookById(bookId);
		if (!book.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Book>(book.get(), HttpStatus.OK);
	}

	// Reading all the libraryBranches in the table
	@GetMapping(path = "/branches",produces = {"application/xml", "application/json"})
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
	public ResponseEntity<BookCopy> readBookCopyById(@PathVariable Integer bookId,
			@PathVariable Integer branchId) {
		Optional<BookCopy> bookCopy = userLibrarian.readBookCopyById(new BookCopyId(bookId, branchId));

		System.out.println(bookId + "  1111111111 " + branchId);
		if(!bookCopy.isPresent()) {
			//if the bookCopy isnt found returns status code 404
			return new ResponseEntity<BookCopy>(new BookCopy(),HttpStatus.NOT_FOUND);
		}
		//Returns 200 if is passes constraints
		return new ResponseEntity<BookCopy>(bookCopy.get(), HttpStatus.OK);
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
		if(bookCopy.getBookId() != null || bookCopy.getBranchId() != null||
				bookCopy.getNoOfCopies() == null) {
			return new ResponseEntity<BookCopy>(new BookCopy(), HttpStatus.BAD_REQUEST);
		}
		bookCopy.setBookId(bookId);
		bookCopy.setBranchId(branchId);
		
		if (!userLibrarian.readLibraryBranchById(branchId).isPresent()
				|| !userLibrarian.readBookById(bookId).isPresent()) {
			//If either of the branch or book 
			return new ResponseEntity<BookCopy>(new BookCopy(), HttpStatus.BAD_REQUEST);
		}
		if (userLibrarian.readBookCopyById(new BookCopyId(bookId, branchId)).isPresent()) {
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
		if(!userLibrarian.readBookCopyById(new BookCopyId(bookId, branchId)).isPresent()) {
			return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
		}
		userLibrarian.deleteBookCopy(bookId, branchId);
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}
	
	
}
