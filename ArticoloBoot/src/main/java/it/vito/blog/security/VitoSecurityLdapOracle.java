package it.vito.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

@EnableAutoConfiguration
@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
@EnableWebMvcSecurity
@Profile("ldapOracle")
public class VitoSecurityLdapOracle  extends WebSecurityConfigurerAdapter{

	@Value("${managerDn}")
	String managerDn; 
	
	@Value("${managerPassword}")
	String managerPassword;
	
	@Value("${urlLdap}")
	String urlLdap;
	
	@Value("${groupBase}")
	String groupBase;
	
	@Value("${userBase}")
	String userBase;
	
	@Value("${groupFilter}")
	String groupFilter;
	
	@Value("${userFilter}")
	String userFilter;


    @Override
    public void configure(WebSecurity security){
        security.ignoring().antMatchers("/css/**","/images/**","/js/**","/webjars/**");
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	http
            .authorizeRequests()
                .antMatchers("/templateAttendere","/messaggiErrore","/items","/","/items/partialsListItem","/items/login","/items/partialsViewSlideShowItem","/items/partialsViewItem","/rest/items","/rest/items/*").permitAll()
                .antMatchers("/items/partialsEditItem","/items/partialsCreateItem", "/items/partialsEditList","/items/partialsCreateSlideShowItem","/items/partialsEditSlideShowItem").hasRole("REDAZIONE")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login").successHandler(new SavedRequestAwareAuthenticationSuccessHandler())
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.ldapAuthentication().contextSource()
        .managerDn(managerDn)
        .managerPassword(managerPassword)
        .url(urlLdap)
        .and()
        .groupSearchBase(groupBase)
        .userSearchBase(userBase)
        .groupSearchFilter(groupFilter)
        .userSearchFilter(userFilter)
        ;
    }

    //Basta aggiungere questo bean e automaticamento springboot nella classe ThymeleafAutoConfiguration
    //la aggiunge all'elenco delle cose da usare e quindi si può utilizzare il namespace 
    //xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3" dentro i template html
    @Bean
    public SpringSecurityDialect springSecurityDialect(){
    	return new SpringSecurityDialect();
    }
    
}
