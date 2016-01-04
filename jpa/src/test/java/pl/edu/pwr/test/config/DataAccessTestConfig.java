package pl.edu.pwr.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import pl.edu.pwr.config.DataAccessConfig;

@Configuration
@Import(DataAccessConfig.class)
public class DataAccessTestConfig {
}
