package pl.edu.pwr.test.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import pl.edu.pwr.dao.BookDao;
import pl.edu.pwr.dao.impl.BookDaoImpl;
import pl.edu.pwr.test.config.DataAccessTestConfig;

@Configuration
@Import(DataAccessTestConfig.class)
public class DataAccessDaoTestConfig {
	
	@Bean
	public BookDao bookDao() {
		return new BookDaoImpl();
	}

}
