package com.melody.config;

import java.util.Locale;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@EnableWebMvc
@Configuration
@ComponentScan({"com.melody.*"})
@EnableTransactionManagement
@EnableAspectJAutoProxy
@Import({SecurityConfig.class})
public class AppConfig extends WebMvcConfigurerAdapter{
	
	@Value("${db.driver.class.name}")
	private String driverClassName;

	@Value("${db.url}")
	private String url;

	@Value("${db.username}")
	private String dbUsername;

	@Value("${db.password}")
	private String dbPassword;
	
	@Bean(name = "filterMultipartResolver")
    public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(10*1024*1024*1024);
		
        return multipartResolver;
    }
	
	@Bean
	public static PropertyPlaceholderConfigurer properties() {
		PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
		Resource[] resources = new ClassPathResource[] { new ClassPathResource("melody.properties") };
		ppc.setLocations(resources);
		ppc.setIgnoreResourceNotFound(false);
		ppc.setIgnoreUnresolvablePlaceholders(false);
		return ppc;
	}
	
	@Bean
	 public LocaleChangeInterceptor localeChangeInterceptor(){
	     LocaleChangeInterceptor localeChangeInterceptor=new LocaleChangeInterceptor();
	     localeChangeInterceptor.setParamName("language");
	     return localeChangeInterceptor;
	 }
	 
	 @Bean(name = "localeResolver")
	 public LocaleResolver sessionLocaleResolver(){
	     SessionLocaleResolver localeResolver=new SessionLocaleResolver();
	     localeResolver.setDefaultLocale(new Locale("en"));
	      
	     return localeResolver;
	 }
	 
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("language");
		registry.addInterceptor(localeChangeInterceptor);
	}
	
	@Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("message");
        return source;
    }
	
	@Bean
	public SessionFactory sessionFactory() {
		LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource());
		builder.scanPackages("com.melody.model")
			.addProperties(getHibernateProperties());
		return builder.buildSessionFactory();
	}
	
	
	private Properties getHibernateProperties() {
		Properties prop = new Properties();
        prop.put("hibernate.format_sql", "true");
        prop.put("hibernate.show_sql", "true");
        prop.put("hibernate.dialect", 
            "org.hibernate.dialect.MySQL5Dialect");
        prop.put("hibernate.hbm2ddl.auto", "update");
        return prop;
	}
	
	@Bean(name = "dataSource")
	public BasicDataSource dataSource() {
		BasicDataSource bds = new BasicDataSource();
		bds.setDriverClassName(driverClassName);
		bds.setUrl(url);
		bds.setUsername(dbUsername);
		bds.setPassword(dbPassword);
		return bds;
	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
        	.addResourceHandler("/resources/**")
        	.addResourceLocations("/resources/")
        	.setCachePeriod(31556926);
    }
	
	@Bean
	@Autowired
	public HibernateTransactionManager txManager(SessionFactory sf) {
		return new HibernateTransactionManager(sf);
	}
	
	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver 
                             = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
}
