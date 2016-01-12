package pl.edu.pwr.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySource(value= "classpath:config/mail.properties", ignoreResourceNotFound = false)
public class MailConfig {
	
	@Autowired
	PropertySourcesPlaceholderConfigurer propConfig;
	
	@Value("${mail.host}")
	private String host;
	
	@Value("${mail.port}")
	private String port;
	
	@Value("${mail.username}")
	private String username;

	@Value("${mail.password}")
	private String password;
	
	@Bean
	public JavaMailSenderImpl mailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(host);
		mailSender.setPort(Integer.parseInt(port));
		mailSender.setUsername(username);
		mailSender.setPassword(password);
		
		Properties properties = new Properties();
		properties.setProperty(protocolKey, protocol);
		properties.setProperty(smtpAuthKey, stmpAuth);
		properties.setProperty(smtpStartTlsKey, stmpStartTls);
		properties.setProperty(mailDebugKey, mailDebug);
		
		mailSender.setJavaMailProperties(properties);
		return mailSender;
	}
	
	private static final String protocolKey = "mail.transport.protocol";
	private static final String smtpAuthKey = "mail.smtp.auth";
	private static final String smtpStartTlsKey = "mail.smtp.starttls.enable";
	private static final String mailDebugKey = "mail.debug";
	
	@Value("${" + protocolKey + "}")
	private String protocol;
	
	@Value("${" + smtpAuthKey + "}")
	private String stmpAuth;
	
	@Value("${" + smtpStartTlsKey + "}")
	private String stmpStartTls;
	
	@Value("${" + mailDebugKey + "}")
	private String mailDebug;
	
}
