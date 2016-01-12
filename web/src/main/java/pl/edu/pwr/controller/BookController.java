package pl.edu.pwr.controller;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.pwr.service.BookService;
import pl.edu.pwr.service.LibraryService;
import pl.edu.pwr.to.BookTo;
import pl.edu.pwr.to.LibraryTo;
import pl.edu.pwr.to.OrderForm;
import pl.edu.pwr.to.UserTo;

@Controller
public class BookController {

	@Autowired
	private BookService bookService;
	
	@Autowired
	private LibraryService libraryService;

	@RequestMapping(value = "/books", method = RequestMethod.GET)
	public String books(HttpSession session, Map<String, Object> params, @RequestParam(required = false) String bookTitle,
	    @RequestParam(required = false) String bookAuthor, @RequestParam(required = false) String bookLibrary) {
		final List<BookTo> books = bookService.findBooksByTitleAuthorLibrary(bookTitle, bookAuthor, bookLibrary);
		final List<LibraryTo> libraries = uniqueLibraries(books);
		params.put("books", books);
		params.put("bookTitle", bookTitle);
		params.put("bookAuthor", bookAuthor);
		params.put("bookLibrary", bookLibrary);
		params.put("libraries", libraries);
		return "bookList";
	}

	private List<LibraryTo> uniqueLibraries(List<BookTo> books) {
		Set<LibraryTo> libraries = new HashSet<>();
		for (BookTo book: books) {
			libraries.addAll(book.getLibraries().stream().map(l -> l.getLibrary()).collect(Collectors.toSet()));
		}
		return libraries.stream().collect(Collectors.toList());
	}

	@RequestMapping(value = "/books/rent/{bookId}", method = RequestMethod.GET)
	public String bookDetails(Map<String, Object> params, @PathVariable("bookId") BigDecimal bookId,
	    @RequestParam BigDecimal libraryId) {
		final BookTo book = bookService.findBookById(bookId);
		final LibraryTo library = libraryService.findLibraryById(libraryId);
		UserTo user = new UserTo(null, "", "", "", new HashSet<>());
		OrderForm order = new OrderForm(user, book, library);
		params.put("order", order);
		params.put("detailsTab", true);
		return "rent";
	}

}
