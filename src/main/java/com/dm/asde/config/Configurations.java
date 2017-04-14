package com.dm.asde.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.dm.asde.filter.AuthenticateFilter;
import com.dm.asde.support.JsonRootRelProvider;
import liquibase.integration.spring.SpringLiquibase;
import org.apache.log4j.Logger;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.Filter;
import java.util.Locale;

/**
 * Created by yinfante on 10/19/16.
 */
@Configuration
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
//@EnableCaching
public class Configurations {

    private Logger logger = Logger.getLogger(Configurations.class);

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;


    @Value("${cloud_name}")
    private String cloudName;

    @Value("${api_key}")
    private String apiKey;

    @Value("${api_secret}")
    private String apiSecret;

    // WebFilter to authenticate users
    @Bean
    public Filter authenticateFilter() {
        return new AuthenticateFilter();
    }

    @Bean
    public RelProvider relProvider() {
        return new JsonRootRelProvider();
    }


    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(new Locale("es")); // Set default Locale as ES
        return slr;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames("i18n/messages");  // name of the resource bundle
        source.setDefaultEncoding("UTF-8");
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }

    @Bean
    public CacheManager cacheManager() {
        return new EhCacheCacheManager();
    }


    @Bean
    public DataSource dataSource() {
        logger.info("Creating data source");

        DataSource dataSource = new DataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);

        return dataSource;
    }


    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase sl = new SpringLiquibase();
        sl.setDataSource(dataSource);
        sl.setChangeLog("classpath:db/dbchangelog.sql");
        sl.setShouldRun(true);
        return sl;
    }

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret));
    }
}
