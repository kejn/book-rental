package pl.edu.pwr.jetty;

import java.io.IOException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.session.HashSessionManager;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import pl.edu.pwr.config.DataAccessConfig;
import pl.edu.pwr.config.WebConfig;

public class EmbeddedJetty {

	private static final Logger logger = LoggerFactory.getLogger(EmbeddedJetty.class);

	private static final int defaultPort = 9721;
	private static final String contextPath = "/book-rental";
	private static final String mappingUrl = "/*";
	private static final String defaultProfile = "dev";
	private static final String webAppDirectory = "webapp";

	private EmbeddedJetty() {
	}

	public static void main(String[] args) throws Exception {
		new EmbeddedJetty().startJetty(defaultPort);
	}

	private void startJetty(int port) throws Exception {
		Server server = new Server(port);
		server.setHandler(getServletContextHandler(getContext()));
		server.start();
		logger.info("Server started at port {}", port);
		logger.info("URL: {}", server.getURI());
		server.join();
	}

	private ServletContextHandler getServletContextHandler(WebApplicationContext context) throws IOException {
		ServletContextHandler contextHandler = new ServletContextHandler();
		contextHandler.setErrorHandler(null);
		contextHandler.setContextPath(contextPath);
		contextHandler.addServlet(new ServletHolder(new DispatcherServlet(context)), mappingUrl);
		contextHandler.addEventListener(new ContextLoaderListener(context));
		contextHandler.setResourceBase(new ClassPathResource(webAppDirectory).getURI().toString());
		contextHandler.setSessionHandler(new SessionHandler(new HashSessionManager()));
		return contextHandler;
	}

	private WebApplicationContext getContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(WebConfig.class, DataAccessConfig.class);
		context.getEnvironment().setDefaultProfiles(defaultProfile);
		return context;
	}

}
