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

import pl.edu.pwr.exception.BookAlreadyRentException;
import pl.edu.pwr.exception.BookNotAvailableException;
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
	private String successUrl = "http://localhost:9721/book-rental/confirmEmail";

	@RequestMapping(value = "/confirmOrder/{bookId}/{libraryId}", method = RequestMethod.POST)
	public String confirmOrder(HttpSession session, @ModelAttribute("order") OrderForm order, Map<String, Object> params,
	    @PathVariable("bookId") BigDecimal bookId, @PathVariable("libraryId") BigDecimal libraryId) {
		final BookTo book = bookService.findBookById(bookId);
		final LibraryTo library = libraryService.findLibraryById(libraryId);
		order.setBook(book);
		order.setLibrary(library);

		UserTo user = (UserTo) session.getAttribute("user");
		if (user != null) {
			try {
				user = userService.rentUserABook(user, book, library);
			} catch (BookAlreadyRentException e) {
				String error = (String) params.get("error");
				params.put("error", appendToMsg(error, "Już wypożyczono tą książkę na ten adres email."));
			} catch (BookNotAvailableException e) {
				String error = (String) params.get("error");
				params.put("error", appendToMsg(error, "Książka jest już niedostępna."));
			}
			order.setUser(user);

		} else {
			user = userService.findUserEqualToEmail(order.getUser().getEmail());
			if (user != null) {
				params.put("error", "Podany adres email już istnieje w bazie danych.");
			}
			try {
				mailSender.send(createMessage(order.getUser().getEmail(), book, library));
			} catch (MailException e) {
				String error = (String) params.get("error");
				params.put("error", appendToMsg(error, e.getMessage()));
			}
		}
		params.put("order", order);
		return "confirmOrder";
	}

	@RequestMapping(value = "/confirmEmail/{email}/{bookId}/{libraryId}", method = RequestMethod.GET)
	public String confirmEmail(HttpSession session, Map<String, Object> params, @PathVariable("email") String email,
	    @PathVariable("bookId") BigDecimal bookId, @PathVariable("libraryId") BigDecimal libraryId) {
		UserTo user = userService.findUserEqualToEmail(email);
		if (user == null) {
			user = new UserTo(null, email, "pass", email, new HashSet<>());
			try {
				user = userService.createNewUser(user);
			} catch (UserNameExistsException | UserEmailExistsException e) {
				String error = (String) params.get("error");
				params.put("error", appendToMsg(error, "Podany adres email już istnieje w bazie danych."));
			}
		}

		final BookTo book = bookService.findBookById(bookId);
		final LibraryTo library = libraryService.findLibraryById(libraryId);

		try {
			user = userService.rentUserABook(user, book, library);
		} catch (BookAlreadyRentException e) {
			String error = (String) params.get("error");
			params.put("error", appendToMsg(error, "Już wypożyczono tą książkę na ten adres email."));
		} catch (BookNotAvailableException e) {
			String error = (String) params.get("error");
			params.put("error", appendToMsg(error, "Książka jest już niedostępna."));
		}
		OrderForm order = new OrderForm(user, book, library);

		params.put("emailConfirmed", true);
		params.put("order", order);
		return "confirmOrder";
	}

	private String appendToMsg(String msg, String text) {
		if (msg != null) {
			msg += "<br />";
		} else {
			msg = new String();
		}
		msg += text;
		return msg;
	}

	private MimeMessagePreparator createMessage(String email, BookTo book, LibraryTo library) {
		MimeMessagePreparator messagePrep = new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage message) throws Exception {
				message.setFrom(new InternetAddress(mailFrom));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
				message.setHeader("Content-Type", "text/html; charset=UTF-8");
				message.setSubject("Book-Rental: potwierdź wypożyczenie");
				String url = successUrl + "/" + email + "/" + book.getId() + "/" + library.getId();
				message.setContent(
		        "<p>Je\u015Bli to ty wypo\u017Cyczy\u0142e\u015B ksi\u0105\u017Ck\u0119 <b>" + book.getTitle()
		            + "</b> w bibliotece <b>" + library.getName()
		            + "</b> kliknij prosz\u0119 w poni\u017Cszy link.<br /><br /><a href=\"" + url + "\">" + url + "</a>",
		        "text/html; charset=UTF-8");
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
		return "signIn";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(Map<String, Object> params) {
		UserTo user = new UserTo();
		params.put("user", user);
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerPost(HttpSession session, @ModelAttribute("user") UserTo user, Map<String, Object> params) {
		params.put("user", user);
		user = userService.findUserEqualToEmailOrName(user.getEmail(), user.getName());
		if (user != null) {
			params.put("error", "Istnieje już konto dla podanego adresu email lub nazwy użytkownika.");
			return "register";
		} else {
			try {
				user = userService.createNewUser((UserTo) params.get("user"));
				session.setAttribute("user", user);
			} catch (UserNameExistsException | UserEmailExistsException e) {
				params.put("error", "Istnieje już konto dla podanego adresu email lub nazwy użytkownika.");
				return "register";
			}
		}
		return "signIn";
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "home";
	}

}
