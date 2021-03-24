package com.linzc.security1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;
import java.util.Random;

/**
 * @author linzc99
 * @create 2021-03-14 16:42
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //配置类方式
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String password = passwordEncoder.encode("123");
//        auth.inMemoryAuthentication().withUser("lin").password(password).roles();
//    }
//
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 开启记住我功能
        http.rememberMe().tokenRepository(persistentTokenRepository()).userDetailsService(userDetailsService).tokenValiditySeconds(60);
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/test/getAll").permitAll();//注销设置,注销后跳转到/test/getAll
        http.exceptionHandling().accessDeniedPage("/unauth.html"); //403页面，没有权限时会403
        http.formLogin()
                .loginPage("/login.html") //自定义自己编写的登录页面
                .loginProcessingUrl("/user/login")  //登录页面url设置
                .defaultSuccessUrl("/test/index").permitAll() //登录成功之后，跳转的路径
                .and().authorizeRequests()
                .antMatchers("/", "/test/hello", "/user/login").permitAll() //设置哪些路径不需要认证就可以直接访问
                //.antMatchers("/test/index").hasAnyAuthority("admins","guest") //当前用户想访问/test/index,必须要拥有admins或guest权限才能访问
                //.antMatchers("/test/index").hasRole("role") //当前用户想访问/test/index,必须要拥有role角色才能访问
                .anyRequest().authenticated()
                .and().csrf().disable(); //关闭csrf防护
    }
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        // 赋值数据源
        jdbcTokenRepository.setDataSource(dataSource);
        // 自动创建表,第一次执行会创建，以后要执行就要删除掉！
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }
}
