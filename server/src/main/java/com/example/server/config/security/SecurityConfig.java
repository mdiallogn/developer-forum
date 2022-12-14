package com.example.server.config.security;

import com.example.server.filter.JwtFilter;
import com.example.server.services.CustomUserDetailsService;
import com.example.server.utils.Const;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService userDetailsService;
    private final JwtFilter jwtFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
//        http.cors().disable();
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/admin/**").hasRole(Const.ADMIN)
                .antMatchers("/api/v1/home").permitAll()
                .antMatchers("/api/v1/users/add").permitAll()
                .antMatchers("/api/v1/users/login").permitAll()
                //.antMatchers("/api/v1/post/**").authenticated()
                .antMatchers("/api/v1/post/**").permitAll()
               // .antMatchers("/api/v1/comment/**").authenticated()
                .antMatchers("/api/v1/comment/**").permitAll()
                //.antMatchers("/api/v1/users/all").authenticated()
                .antMatchers("/api/v1/users/all").permitAll()
                .and().exceptionHandling().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    }
}
