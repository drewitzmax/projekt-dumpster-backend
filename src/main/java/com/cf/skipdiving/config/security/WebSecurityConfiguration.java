package com.cf.skipdiving.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication().dataSource(dataSource)
                    .usersByUsernameQuery("SELECT username, password, enabled FROM skip_diving.sd_user WHERE username=?")
                    .authoritiesByUsernameQuery("SELECT username, authority FROM skip_diving.sd_user WHERE username=?")
                    .passwordEncoder(new BCryptPasswordEncoder()).
                and()
                    .jdbcAuthentication().dataSource(dataSource)
                    .usersByUsernameQuery("SELECT email, password, enabled FROM skip_diving.provider WHERE email=?")
                    .authoritiesByUsernameQuery("SELECT email, authority FROM skip_diving.provider WHERE email=?")
                    .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests().antMatchers(HttpMethod.GET).authenticated()
                    .antMatchers(HttpMethod.POST, "/offer").hasAuthority("provider")
                .and()
                    .httpBasic()
                .and()
                    .cors()
                .and()
                    .csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.POST, "/user")
                .antMatchers(HttpMethod.POST, "/provider")
                .antMatchers(HttpMethod.GET, "/provider")
                .antMatchers(HttpMethod.GET, "/offer");
    }

}
