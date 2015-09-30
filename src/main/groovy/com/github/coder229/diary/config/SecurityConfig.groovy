package com.github.coder229.diary.config

import com.github.coder229.diary.security.StatelessAuthFilter
import com.github.coder229.diary.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

/**
 * Created by scott on 9/2/2015.
 */
@Configuration
@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserRepository accountRepository

    @Autowired
    StatelessAuthFilter statelessAuthFilter

    @Autowired
    void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
    }

    @Override
    UserDetailsService userDetailsService() {
        new UserDetailsService() {
            @Override
            UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                com.github.coder229.diary.user.User account = accountRepository.findByUsername(username)

                if (account == null) {
                    throw new UsernameNotFoundException("could not find the account '"
                            + username + "'")
                }

                new User(account.getUsername(), account.getPassword(),
                        account.isEnabled(), true, true, true,
                        AuthorityUtils.createAuthorityList("USER"))
            }
        }
    }

    @Override
    void configure(HttpSecurity http) throws Exception {
        // TODO http://angularjs-best-practices.blogspot.com/2013/07/angularjs-and-xsrfcsrf-cross-site.html

        // @formatter:off
        http.csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
            .antMatchers("/login").permitAll()
            .antMatchers("/**/*.html").permitAll()
            .antMatchers("/scripts/**").permitAll()
            .antMatchers("/styles/**").permitAll()
            .antMatchers("/userinfo").authenticated()
            .antMatchers("/api/**").authenticated()
        .and()
            .addFilterBefore(statelessAuthFilter, UsernamePasswordAuthenticationFilter)
        // @formatter:on
    }
}
