package it.vito.blog.security;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@EnableAutoConfiguration
@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
@EnableWebMvcSecurity
public class VitoSecurity  extends WebSecurityConfigurerAdapter{



    @Override
    public void configure(WebSecurity security){
        security.ignoring().antMatchers("/css/**","/images/**","/js/**","/webjars/**");
    }
    
}
