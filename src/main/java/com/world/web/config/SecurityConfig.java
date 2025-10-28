package com.world.web.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    /**
     * Performs Authorization for each and every request
     * @param httpSecurity as input parameter
     * @return SecurityFilterChain
     * @throws Exception in-case of Error
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                //.authorizeHttpRequests(request -> request.anyRequest().permitAll())
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/").hasAnyAuthority("STAFF_MEMBER", "ADMIN")
                                .requestMatchers("/v1/issue/new").hasAuthority("ADMIN")
                                .requestMatchers("/v1/update").hasAuthority("ADMIN")
                                .requestMatchers("/v1/delete").hasAuthority("ADMIN")
                        .anyRequest()
                        .authenticated())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public SecurityFilterChain actuatorChain(HttpSecurity httpSecurity) throws Exception  {
        return httpSecurity.securityMatcher(EndpointRequest.toAnyEndpoint())
                .authorizeHttpRequests(request -> request.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    /**
     *
     * @return DaoAuthenticationProvider
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider  = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withUsername("user")
                        .authorities("ADMIN", "STAFF_MEMBER")
                        .password(passwordEncoder().encode("user"))
                        .build();
        UserDetails admin =
                User.withUsername("admin")
                    .authorities("ADMIN", "STAFF_MEMBER")
                    .password(passwordEncoder().encode("admin"))
                    .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
