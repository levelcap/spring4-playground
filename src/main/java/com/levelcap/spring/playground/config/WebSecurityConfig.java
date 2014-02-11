package com.levelcap.spring.playground.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Creates the security rules for our application.
 * 
 * @author Dave Cohen
 * 
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{

    /**
     * @param http
     *            Allows configuring web based security for specific http
     *            requests.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        /**
         * Breaking this beast down:
         * 
         * .csrf().disable() - Allows cross domain requests by disabling the
         * default CSRF protection
         * 
         * .authorizeRequests() - Allows restricting access based on request
         * 
         * .antMatches("/assets/**").permitAll() - Allows all requests to our
         * assets folder without auth
         * 
         * .anyRequest().authenticated() - All other requests should be
         * authenticated
         * 
         * .and().logout().logoutSuccessUrl("/login.html?logout").logoutUrl(
         * "/logout.html").permitAll() - Adds logout support, providing a
         * success URL and the URL that handles logout. All requests are allowed
         * to the logout URL.
         * 
         * .and().formLogin().defaultSuccessUrl("/index.html").loginPage(
         * "/login.html").failureUrl("/login.html?error").permitAll() - Adds
         * login support via a login page at, and permits all requests to that
         * page.
         * 
         */
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/assets/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .logout()
                .logoutSuccessUrl("/login.html?logout")
                .logoutUrl("/logout.html")
                .permitAll()
                .and()
                .formLogin()
                .defaultSuccessUrl("/index.html")
                .loginPage("/login.html")
                .failureUrl("/login.html?error")
                .permitAll();
    }

    /**
     * Configure the users for the web application.
     * 
     * @param auth
     *            AuthenticationManagerBuilder used to add authenticated users
     *            for the application.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth
                .inMemoryAuthentication()
                .withUser("jshmoe").password("password").roles("USER").and()
                .withUser("dcohen").password("security").roles("ADMIN", "USER");
    }
}