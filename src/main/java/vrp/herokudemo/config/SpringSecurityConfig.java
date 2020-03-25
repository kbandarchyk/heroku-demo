package vrp.herokudemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import vrp.herokudemo.repository.UserRepository;

@EnableWebFluxSecurity
public class SpringSecurityConfig {
    
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(final ServerHttpSecurity http ) {
        return http.authorizeExchange()
                   .pathMatchers( "/api/v1.0/customer/**" ).authenticated()
                   .anyExchange().permitAll()
                   .and().httpBasic()
                   .and().csrf().disable()
                   .build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public CustomReactiveUserDetailsService customReactiveUserDetailsService( final UserRepository userRepository
                                                                            , final PasswordEncoder passwordEncoder ) {
        return new CustomReactiveUserDetailsService( userRepository, passwordEncoder );
    }

}
