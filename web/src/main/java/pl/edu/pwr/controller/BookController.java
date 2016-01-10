package pl.edu.pwr.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.pwr.service.BookService;
import pl.edu.pwr.to.BookTo;
import pl.edu.pwr.to.LibraryTo;

@Controller
public class BookController {

	@Autowired
	private BookService bookService;

	@RequestMapping(value = "/books", method = RequestMethod.GET)
	public String books(Map<String, Object> params, @RequestParam(required = false) String bookTitle,
	    @RequestParam(required = false) String bookAuthor, @RequestParam(required = false) String bookLibrary) {
		final List<BookTo> books = bookService.findBooksByTitleAuthorLibrary(bookTitle, bookAuthor, bookLibrary);
		final List<LibraryTo> libraries = uniqueLibraries(books);
		params.put("books", books);
		params.put("bookTitle", bookTitle);
		params.put("bookAuthor", bookAuthor);
		params.put("bookLibrary", bookLibrary);
		System.out.println("\n\n\n\n\n\n\n");
		System.out.println(bookLibrary);
		System.out.println("\n\n\n\n\n\n\n");
		params.put("libraries", libraries);
		return "bookList";
	}

	private List<LibraryTo> uniqueLibraries(List<BookTo> books) {
		Set<LibraryTo> libraries = new HashSet<>();
		for(BookTo book : books) {
			libraries.addAll(book.getLibraries().stream().map(l -> l.getLibrary()).collect(Collectors.toSet()));
		}
		return libraries.stream().collect(Collectors.toList());
	}
}
