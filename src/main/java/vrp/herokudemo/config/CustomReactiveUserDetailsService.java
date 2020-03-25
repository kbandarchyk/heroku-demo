package vrp.herokudemo.config;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;
import vrp.herokudemo.repository.UserRepository;
import vrp.herokudemo.repository.criteria.UserRepoCriteria;
import vrp.herokudemo.repository.dto.UserRepoDto;

public class CustomReactiveUserDetailsService implements ReactiveUserDetailsService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public CustomReactiveUserDetailsService( final UserRepository userRepository
                                           , final PasswordEncoder passwordEncoder ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public Mono<UserDetails> findByUsername( final String username ) {
        
        return userRepository.fetchBy( UserRepoCriteria.builder()
                                                       .login( username )
                                                       .isDeleted( false )
                                                       .build() )
                             .switchIfEmpty( Mono.error( new UsernameNotFoundException( username ) ) )
                             .map( this::toUserDetails );
    }
    
    
    ////////////////
    // Util
    ////
    
    
    private UserDetails toUserDetails( final UserRepoDto userRepoDto ) {
        
        return User.withUsername( userRepoDto.getLogin() )
                   .password( passwordEncoder.encode( userRepoDto.getPassword() ) )
                   .roles( "USER" )
                   .build();
    }
}
