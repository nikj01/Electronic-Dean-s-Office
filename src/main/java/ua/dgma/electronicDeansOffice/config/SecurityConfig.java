package ua.dgma.electronicDeansOffice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.dgma.electronicDeansOffice.services.PersonDetailsService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .authenticated();
//                .hasAnyRole(
//                        "ROOT",
//                        "ADMIN",
//                        "DEANERY_WORKER",
//                        "TEACHER",
//                        "STUDENT",
//                        "HEAD_OF_THE_DEPARTMENT"
//                );
//                .antMatchers("/auth/login", "/auth/logout", "/error").permitAll()
//                .anyRequest().hasAnyRole(
//                        "ROOT",
//                        "ADMIN",
//                        "DEANERY_WORKER",
//                        "TEACHER",
//                        "STUDENT",
//                        "HEAD_OF_THE_DEPARTMENT")
//            .and()
//                .logout()
//                .logoutUrl("auth/logout")
//                .logoutSuccessUrl("/auth/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
