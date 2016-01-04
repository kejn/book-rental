package pl.edu.pwr.config;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = { "pl.edu.pwr" })
@EnableAspectJAutoProxy
@EnableTransactionManagement
@Import(DatabaseConfig.class)
public class DataAccessConfig {

	public static final String PERSISTANCE_UNIT_NAME = "bookRentalPersistance";
	private static final String packagesToScan = "pl.edu.pwr.entity";

	@Autowired
	private DatabaseConfig databaseConfig;

	@Autowired
	private JpaVendorAdapter hibernateJpaVendorAdapter;

	@Configuration
	static class VendorAdapter {
		@Bean
		public JpaVendorAdapter hibernateJpaVendorAdapter() {
			HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
			adapter.setDatabase(Database.ORACLE);
			adapter.setShowSql(true);
			adapter.setGenerateDdl(true);
			return adapter;
		}
	}

	@Bean
	public PlatformTransactionManager transactionManager() throws SQLException, IOException {
		return new JpaTransactionManager(entityManagerFactory().getObject());
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws SQLException, IOException {
		LocalContainerEntityManagerFactoryBean eMFactory = new LocalContainerEntityManagerFactoryBean();
		eMFactory.setPersistenceUnitName(PERSISTANCE_UNIT_NAME);
		eMFactory.setDataSource(databaseConfig.dataSource());
		eMFactory.setJpaProperties(hibernateProperties().getObject());
		eMFactory.setJpaVendorAdapter(hibernateJpaVendorAdapter);
		eMFactory.setPackagesToScan(packagesToScan);
		return eMFactory;
	}

	@Bean
	public PropertiesFactoryBean hibernateProperties() {
		PropertiesFactoryBean properties = new PropertiesFactoryBean();
		properties.setLocation(new ClassPathResource("config/hibernate.properties"));
		return properties;
	}

}
