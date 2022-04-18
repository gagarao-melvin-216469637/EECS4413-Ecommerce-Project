package org.o7planning.krispykart.config;

import org.o7planning.krispykart.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// allows for Spring to resolve and inject collaborating beans into this bean using UserDetailsServiceImpl userDetailsService
   @Autowired
   UserDetailsServiceImpl userDetailsService;

   // Implementation of PasswordEncoder that uses the BCrypt strong hashing function.
   @Bean
   public BCryptPasswordEncoder passwordEncoder() {
      BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
      return bCryptPasswordEncoder;
   }

   @Autowired
   public void configureGlobal(AuthenticationManagerBuilder user) throws Exception {

	  // use the Service to locate the User within the database and set the Password Encoder
      user.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

   }

   // HttpSecurity allows for configuring web based security on http requests
   @Override
   protected void configure(HttpSecurity http) throws Exception {

	  // first we must disable the CSRF protection which is enabled by default.
      http.csrf().disable();

      // can only be accessed if the user has a role under 'ROLE_MANAGER' or 'ROLE_EMPLOYEE' (gets redirected to /admin/login if the role is not satisfied)
      http.authorizeRequests().antMatchers("/admin/orderList", "/admin/order", "/admin/accountInfo").access("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')");

      // admin permissions are given to managers only
      http.authorizeRequests().antMatchers("/admin/product").access("hasRole('ROLE_ADMIN')");

      // prevent users without role permission from accessing pages
      http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

      http.authorizeRequests().and().formLogin() // login form configuration

            .loginProcessingUrl("/j_spring_security_check")
            .loginPage("/admin/login")
            .defaultSuccessUrl("/admin/accountInfo").failureUrl("/admin/login?error=true") // success or failure to admin role
            .usernameParameter("userName").passwordParameter("password") // parameters for users username and password
            .and().logout().logoutUrl("/admin/logout").logoutSuccessUrl("/"); //return to homepage after logging out

   }
}