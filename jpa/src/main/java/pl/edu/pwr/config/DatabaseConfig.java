package pl.edu.pwr.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import oracle.jdbc.pool.OracleDataSource;

@Configuration
@PropertySource(value = "classpath:config/oracle.properties", ignoreResourceNotFound = false)
public class DatabaseConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

	@Value("${oracle.user}")
	private String user;

	@Value("${oracle.password}")
	private String password;

	@Value("${oracle.url}")
	private String url;

	/** 
	 * Allows to parse values using @Value("${variable.in.properties.file}") before other Spring beans are created.
	 * <br>
	 * <b>IMPORTANT!</b> It has to be <code>static</code>.
	 * @see <a href="http://www.javacodegeeks.com/2013/07/spring-bean-and-propertyplaceholderconfigurer.html">
	 * http://www.javacodegeeks.com/2013/07/spring-bean-and-propertyplaceholderconfigurer.html</a>
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	@Bean
	public DataSource dataSource() throws SQLException {
		OracleDataSource dataSource = new OracleDataSource();
		dataSource.setUser(user);
		dataSource.setPassword(password);
		dataSource.setURL(url);
		logger.info("Setting DB connection parameters: {}.", this.toString());
		return dataSource;
	}
	
	@Override
	public String toString() {
		return "user [" + user + "], password [" + password + "], url [" + url + "]";
	}
	
}
