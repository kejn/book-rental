package pl.edu.pwr.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import pl.edu.pwr.config.DataAccessConfig;
import pl.edu.pwr.dao.BookDao;
import pl.edu.pwr.dao.impl.BookDaoImpl;

@Configuration
@Import(DataAccessConfig.class)
public class DataAccessDaoTestConfig {
	
	@Bean
	public BookDao bookDao() {
		return new BookDaoImpl();
	}

}
