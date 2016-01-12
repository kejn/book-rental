package pl.edu.pwr.controller;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.edu.pwr.exception.UserEmailExistsException;
import pl.edu.pwr.exception.UserNameExistsException;
import pl.edu.pwr.service.BookService;
import pl.edu.pwr.service.LibraryService;
import pl.edu.pwr.service.UserService;
import pl.edu.pwr.to.BookTo;
import pl.edu.pwr.to.LibraryTo;
import pl.edu.pwr.to.OrderForm;
import pl.edu.pwr.to.UserTo;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private BookService bookService;

	@Autowired
	private LibraryService libraryService;

	@Autowired
	JavaMailSender mailSender;

	private final String mailFrom = "no-reply@book-rental.com";

	@RequestMapping(value = "/confirmOrder/{bookId}/{libraryId}", method = RequestMethod.POST)
	public String confirmOrder(HttpSession session, @ModelAttribute("order") OrderForm order, Map<String, Object> params,
	    @PathVariable("bookId") BigDecimal bookId, @PathVariable("libraryId") BigDecimal libraryId) {
		final BookTo book = bookService.findBookById(bookId);
		final LibraryTo library = libraryService.findLibraryById(libraryId);
		order.setBook(book);
		order.setLibrary(library);

		UserTo user = (UserTo) session.getAttribute("user");
		if (user != null) {
			order.setUser(user);
		} else {
			try {
				mailSender.send(createMessage(order.getUser().getEmail(), book, library));
			} catch(MailException e) {
				System.out.println(e.getMessage());
			}
		}
		params.put("order", order);
		return "confirmOrder";
	}

	@RequestMapping(value = "/confirmEmail/{email}/{bookId}/{libraryId}", method = RequestMethod.GET)
	public String confirmEmail(HttpSession session, Map<String, Object> params, @PathVariable("email") String email,
	    @PathVariable("bookId") BigDecimal bookId, @PathVariable("libraryId") BigDecimal libraryId)
	        throws UserNameExistsException, UserEmailExistsException {
		UserTo user = new UserTo(null, "", "", email, new HashSet<>());
		user = userService.createNewUserWithNameLikeId(user);
		final BookTo book = bookService.findBookById(bookId);
		final LibraryTo library = libraryService.findLibraryById(libraryId);
		OrderForm order = new OrderForm(user, book, library);
		return confirmOrder(session, order, params, bookId, libraryId);
	}

	private String successUrl = "http://localhost:9721/book-rental/confirmEmail";

	private MimeMessagePreparator createMessage(String email, BookTo book, LibraryTo library) {
		MimeMessagePreparator messagePrep = new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage message) throws Exception {
				message.setFrom(new InternetAddress(mailFrom));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
				message.setSubject("Book-Rental: potwierdź wypożyczenie");
				String url = successUrl + "/" + email + "/" + book.getId() + "/" + library.getId();
				message.setContent("<p>Jeśli to ty wypożyczyłeś książkę <b>" + book.getTitle() + "</b> w bibliotece <b>"
		        + library.getName() + "</b> kliknij proszę w poniższy link.<br /><br /><a href=\"" + url + "\">url</a>",
		        "text/html");
			}
		};
		return messagePrep;
	}

	@RequestMapping(value = "/signIn", method = RequestMethod.GET)
	public String signIn(Map<String, Object> params) {
		UserTo user = new UserTo();
		params.put("user", user);
		return "signIn";
	}

	@RequestMapping(value = "/signIn", method = RequestMethod.POST)
	public String signInPost(HttpSession session, @ModelAttribute("user") UserTo user, Map<String, Object> params) {
		user = userService.findUserEqualToNameVerifyPassword(user.getName(), user.getPassword());
		if (user == null) {
			params.put("error", "Podana nazwa użytkownika lub hasło są nieprawidłowe.");
		} else {
			session.setAttribute("user", user);
		}
		return "home";
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "home";
	}

}
