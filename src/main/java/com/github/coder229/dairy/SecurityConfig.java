package com.github.coder229.dairy;

import com.github.coder229.dairy.model.Account;
import com.github.coder229.dairy.repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by scott on 9/2/2015.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccountsRepository accountRepository;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // @formatter:off
        auth.userDetailsService(userDetailsService())
//            .inMemoryAuthentication()
//            .withUser("user").password("password").roles("USER")
                ;
        // @formatter:on
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Account account = accountRepository.findByUsername(username);
                if (account != null) {
                    return new User(account.getUsername(), account.getPassword(),
                            account.isEnabled(), true, true, true,
                            AuthorityUtils.createAuthorityList("USER"));
                } else {
                    throw new UsernameNotFoundException("could not find the account '"
                            + username + "'");
                }
            }

        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO http://angularjs-best-practices.blogspot.com/2013/07/angularjs-and-xsrfcsrf-cross-site.html

        // @formatter:off
//        http
//            .csrf().disable()
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        .and()
//            .authorizeRequests()
//                .antMatchers("/api").permitAll()
//                .anyRequest().authenticated()
//        .and()
//            .anonymous()
//                .antMatchers("/index.html").permitAll()
//        ;

        http.authorizeRequests().anyRequest().fullyAuthenticated()
            .and()
                .httpBasic()
            .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // @formatter:on

    }
}
