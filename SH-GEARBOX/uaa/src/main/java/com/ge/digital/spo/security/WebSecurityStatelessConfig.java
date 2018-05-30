package com.ge.digital.spo.security;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ge.digital.spo.security.dynamicalRole.MyAccessDecisionManager;
import com.ge.digital.spo.security.dynamicalRole.MySecurityMetadataSource;

@Configuration
@EnableWebSecurity
class WebSecurityStatelessConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	CustomAuthenticationProvider customAuthenticationProvider;
	@Resource  
    private MySecurityMetadataSource mySecurityMetadataSource; 
    // set HTTP rule for validation
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // close csrf  verification
        http.csrf().disable()
                // authentication for request
                .authorizeRequests().anyRequest().authenticated().withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {  
                    public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {  
                        fsi.setSecurityMetadataSource(mySecurityMetadataSource);  
                        fsi.setAccessDecisionManager(accessDecisionManager());  
                        try {
							fsi.setAuthenticationManager(authenticationManagerBean());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}  
                        return fsi;  
                    }  
                })
                // let all static resource and /* go
                .antMatchers("/js/**").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/fonts/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/upload/**").permitAll()
                .antMatchers("/*.ico").permitAll()  
                .antMatchers("/").permitAll()
                // ok for post /login
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                // check authority
                .antMatchers("/hello").hasAuthority("AUTH_WRITE")
                // check admin
                .antMatchers("/api/resource/selectAll").hasRole("ADMIN")
                // authenticate for all request
                .anyRequest().authenticated()
            .and()
                // add a new filter to handle /login to JWTLoginFilter to handle related content
                .addFilterBefore(new JwtLoginFilter("/login", authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)
                // check if a token is valid
                .addFilterBefore(new JwtAuthenticationFilter(),
                        UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 使用自定义身份验证组件
        auth.authenticationProvider(customAuthenticationProvider);

    }
    
    @Bean(name = "accessDecisionManager")  
    public AccessDecisionManager accessDecisionManager() {  
        List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList();  
        decisionVoters.add(new RoleVoter());  
        decisionVoters.add(new AuthenticatedVoter());  
        decisionVoters.add(webExpressionVoter());// start a expression voter
        MyAccessDecisionManager accessDecisionManager = new MyAccessDecisionManager(decisionVoters);  
        return accessDecisionManager;  
    } 
    @Bean(name = "expressionHandler")  
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {  
        DefaultWebSecurityExpressionHandler webSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();  
        return webSecurityExpressionHandler;  
    } 
    @Bean(name = "expressionVoter")  
    public WebExpressionVoter webExpressionVoter() {  
        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();  
        webExpressionVoter.setExpressionHandler(webSecurityExpressionHandler());  
        return webExpressionVoter;  
    }
}