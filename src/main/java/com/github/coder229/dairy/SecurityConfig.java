package com.github.coder229.dairy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Created by scott on 9/2/2015.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // @formatter:off
        auth
            .inMemoryAuthentication()
            .withUser("user").password("password").roles("USER");
        // @formatter:on
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO http://angularjs-best-practices.blogspot.com/2013/07/angularjs-and-xsrfcsrf-cross-site.html

        // @formatter:off
        http
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .authorizeRequests()
            .antMatchers("/api").permitAll()
        .and()
            .csrf().disable();
        // @formatter:on
    }
}
