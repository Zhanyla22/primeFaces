package org.xtremebiker.jsfspring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.xtremebiker.jsfspring.service.impl.UserServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /** The utilisateur service. */
    @Autowired
    private UserDetailsService utilisateurService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // require all requests to be authenticated except for the resources
        http
                .authorizeRequests()
                .antMatchers("/ui/login.xhtml").permitAll() // Public resources
                .antMatchers("/ui/test.xhtml").hasRole("ADMIN") // Admin resources
                .antMatchers("/ui/user.xhtml").hasRole("USER")   // User resources
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/ui/login.xhtml")
                .loginProcessingUrl("/login")
                .usernameParameter("userName")
                .passwordParameter("password")
                .failureUrl("/ui/login.jsf?error=true")
                .defaultSuccessUrl("/ui/test.xhtml");
        // logout
        http.logout().logoutUrl("/logout").invalidateHttpSession(true).logoutSuccessUrl("/ui/login.xhtml");
        // not needed as JSF 2.2 is implicitly protected against CSRF
        http.csrf().disable();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // Setting Service to find User in the database.
        // And Setting PassswordEncoder
        auth.userDetailsService(utilisateurService).passwordEncoder(passwordEncoder());

    }}
