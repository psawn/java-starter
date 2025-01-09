package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.sql.DataSource;

@Configuration
public class DemoSecurityConfig {
//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager() {
//        UserDetails john = User.builder().username("john").password("{noop}admin").roles(ROLE.EMPLOYEE).build();
//        UserDetails mary = User.builder()
//                               .username("mary")
//                               .password("{noop}admin")
//                               .roles(ROLE.EMPLOYEE, ROLE.MANAGER)
//                               .build();
//        UserDetails susan = User.builder()
//                                .username("susan")
//                                .password("{noop}admin")
//                                .roles(ROLE.EMPLOYEE, ROLE.MANAGER, ROLE.ADMIN)
//                                .build();
//
//        return new InMemoryUserDetailsManager(john, mary, susan);
//    }

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        // setUsersByUsernameQuery + setAuthoritiesByUsernameQuery -> use custom table for authentication and authorization
        jdbcUserDetailsManager.setUsersByUsernameQuery("select user_id, pw, active from members where user_id=?");

        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select user_id, role from roles where user_id=?");

        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configure -> configure.requestMatchers(HttpMethod.GET, "/api/employees")
                                                         .hasRole(ROLE.EMPLOYEE)
                                                         .requestMatchers(HttpMethod.GET, "/api/employees/**")
                                                         .hasRole(ROLE.EMPLOYEE)
                                                         .requestMatchers(HttpMethod.POST, "/api/employees")
                                                         .hasRole(ROLE.MANAGER)
                                                         .requestMatchers(HttpMethod.PUT, "/api/employees")
                                                         .hasRole(ROLE.MANAGER)
                                                         .requestMatchers(HttpMethod.DELETE, "/api/employees/**")
                                                         .hasRole(ROLE.ADMIN)
                                                         .requestMatchers("/security")
                                                         .hasRole(ROLE.EMPLOYEE)
                                                         .requestMatchers("/leaders/**")
                                                         .hasRole(ROLE.MANAGER)
                                                         .requestMatchers("/systems/**")
                                                         .hasRole(ROLE.ADMIN)
                                                         .anyRequest()
                                                         .permitAll())
            .formLogin(form -> form.loginPage("/showMyLoginPage")
                                   .loginProcessingUrl("/authenticateTheUser")
                                   .permitAll())
            .logout(logout -> logout.permitAll())
            .exceptionHandling(configure -> configure.accessDeniedPage("/access-denied"));

        http.httpBasic(Customizer.withDefaults());

        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    public static class ROLE {
        public static final String EMPLOYEE = "EMPLOYEE";
        public static final String MANAGER = "MANAGER";
        public static final String ADMIN = "ADMIN";
    }
}
