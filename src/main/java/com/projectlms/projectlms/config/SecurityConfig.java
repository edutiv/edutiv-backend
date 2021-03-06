package com.projectlms.projectlms.config;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.projectlms.projectlms.security.SecurityFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userService;
    private final SecurityFilter securityFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors().and().csrf().disable()
            .authorizeRequests()
            .antMatchers("/user/register", "/user/login", "/h2-console/**").permitAll()
            .antMatchers(HttpMethod.GET, "/category/**").permitAll()
            .antMatchers(HttpMethod.GET, "/course/**").permitAll()
            .antMatchers(HttpMethod.GET, "/faq").permitAll()    
            .antMatchers(HttpMethod.GET, "/enrolled/courses/**").permitAll()  
            .antMatchers(HttpMethod.GET, "/enrolled/download-report/**").permitAll() 
            .antMatchers("/**").hasAnyRole("ADMIN", "USER", "MENTOR")
            .anyRequest().authenticated();

        http.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
    }
    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "HEAD", "DELETE"));
        UrlBasedCorsConfigurationSource source = new
                UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors.applyPermitDefaultValues());
        return source;
    }
}





// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// import com.projectlms.projectlms.security.SecurityFilter;
// import com.projectlms.projectlms.service.CustomUserDetailsService;

// import lombok.RequiredArgsConstructor;

// @Configuration
// @EnableWebSecurity
// @EnableGlobalMethodSecurity(
//         prePostEnabled = true)
// @RequiredArgsConstructor
// public class SecurityConfig extends WebSecurityConfigurerAdapter{
//     private final UserDetailsService userService;
//     private final SecurityFilter securityFilter;
    
//     // @Autowired
// 	// CustomUserDetailsService userDetailsService;

//     // @Autowired
//     // private JwtAuthenticationEntryPoint unauthorizedHandler;

//     // @Bean
// 	// public SecurityFilter authenticationJwtTokenFilter() {
// 	// 	return new SecurityFilter();
// 	// }
    
//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     @Override
//     protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//         auth.userDetailsService(userService)
//             .passwordEncoder(passwordEncoder());
//     }

//     @Bean
//     @Override
//     protected AuthenticationManager authenticationManager() throws Exception {
//         return super.authenticationManager();
//     }

//     @Override
//     protected void configure(HttpSecurity http) throws Exception {
//         http.cors().and().csrf().disable()
//             //.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//             .authorizeRequests()
//             .antMatchers("/api/auth/**").permitAll()
//             .antMatchers("/h2-console/**").permitAll()
//             .antMatchers("/**").hasAnyRole("ADMIN", "USER")
//             .anyRequest().authenticated();
//         //remove session
//         http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//         //filter jwt
        
//         http.headers().frameOptions().disable();
//         http.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
//     }
// }
