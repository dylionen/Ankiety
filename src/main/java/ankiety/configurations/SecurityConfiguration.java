package ankiety.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    final UserDetailsService userDetailService;

    public SecurityConfiguration(UserDetailsService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/new/**").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/bootstrap**").permitAll()
              /*  .antMatchers("/user/**").hasAnyAuthority("ADMIN", "EMPLOYEE", "EMPLOYER")
                .antMatchers("/company/**").hasAnyAuthority("ADMIN", "EMPLOYER")
                .antMatchers("/job/**").permitAll()

                .antMatchers("/register").permitAll()

                .antMatchers("/bs4**").permitAll()
                .antMatchers("/fonts**").permitAll()
                .antMatchers("/images**").permitAll()
                .antMatchers("/css**").permitAll()
                .antMatchers("/scss**").permitAll()*/
                .antMatchers("/").permitAll()
                .and()
                .formLogin().loginPage("/login")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2/**");
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static final int JOBS_PER_PAGE = 7;
    public static final String UPLOAD_DIR = "C:/Uploads/";
}
