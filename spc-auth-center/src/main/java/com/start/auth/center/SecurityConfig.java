package com.start.auth.center;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.start.framwork.jwt.JwtAuthenticationConfig;
import com.start.framwork.jwt.JwtUsernamePasswordAuthenticationFilter;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	JwtAuthenticationConfig config;

	@Bean
	public JwtAuthenticationConfig jwtConfig() {
		return new JwtAuthenticationConfig();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		auth.inMemoryAuthentication().withUser("admin").password(encoder.encode("admin")).roles("ADMIN", "USER").and()
				.withUser("shuaicj").password(encoder.encode("shuaicj")).roles("USER");
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable().logout().disable()
				.addFilterBefore(new JwtUsernamePasswordAuthenticationFilter(config, authenticationManager()),
						UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests().antMatchers("/").permitAll()
				.antMatchers("/user/**").hasRole("USER")
				.and()
				.formLogin().loginPage("/login").defaultSuccessUrl("/user")
				.and()
				.logout().logoutUrl("/logout")
				.logoutSuccessUrl("/login");
	}
}
