package com.yoti.api.examples.springboot;

import com.yoti.api.client.YotiClient;
import com.yoti.api.examples.springboot.web.DemoController;
import com.yoti.api.spring.YotiProperties;
import com.yoti.api.spring.security.auth.YotiAuthenticationProvider;
import com.yoti.api.spring.security.service.YotiUserService;
import com.yoti.api.spring.security.web.YotiAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGIN = "/login";
    private static final String LOGOUT_SUCCESS_PAGE = "/logout-success.html";
    private static final String CSS = "/css/**";

    @Autowired
    private YotiClient client;

    @Autowired
    private YotiUserService userService;

    @Autowired
    private YotiProperties yotiProperties;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(LOGOUT_SUCCESS_PAGE, LOGIN, CSS, LOGOUT_SUCCESS_PAGE).permitAll()
                .anyRequest().authenticated()
                .and()
                .logout().clearAuthentication(true).invalidateHttpSession(true).logoutSuccessHandler(logoutSuccessHandler()).permitAll()
                .and()
                .formLogin().disable()
                .exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("https://www.yoti.com/connect/" + yotiProperties.getApplicationId())).and()
                .addFilterBefore(yotiAuthFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    private LogoutSuccessHandler logoutSuccessHandler() {
        final SimpleUrlLogoutSuccessHandler handler = new SimpleUrlLogoutSuccessHandler();
        handler.setDefaultTargetUrl(LOGOUT_SUCCESS_PAGE);
        handler.setAlwaysUseDefaultTargetUrl(true);
        return handler;
    }

    private YotiAuthenticationFilter yotiAuthFilter() throws Exception {
        final YotiAuthenticationFilter filter = new YotiAuthenticationFilter(authenticationManager(), LOGIN);
        filter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler(DemoController.MAPPING_URI));
        return filter;
    }

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new YotiAuthenticationProvider(client, userService));
        auth.eraseCredentials(true);
    }
}
