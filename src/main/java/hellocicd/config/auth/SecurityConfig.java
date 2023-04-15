package hellocicd.config.auth;

import hellocicd.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers("/**", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                .and()
                .csrf().ignoringRequestMatchers(toH2Console())
                .and()
                .csrf().ignoringRequestMatchers("/**")
                .and()
                .headers().frameOptions().sameOrigin()
                .and()
                .logout().logoutSuccessUrl("/")
                .and()
                .oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);
        return http.build();
    }
}
