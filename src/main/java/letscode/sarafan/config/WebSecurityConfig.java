package letscode.sarafan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers("/").permitAll() // даже неавторизованные пользователи могут зайти на основной сайт
                .anyRequest().authenticated()
                .and()
                //.csrf().disable() // отключаем csrf токен
                .oauth2Login()
                .successHandler(successHandler()); // обработчик успешной авторизации

/*        http
                .antMatcher("/**").authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login();*/
    }

    @Bean
    SarafanAuthenticationSuccessHandler successHandler() {
        return new SarafanAuthenticationSuccessHandler();
    }
}
