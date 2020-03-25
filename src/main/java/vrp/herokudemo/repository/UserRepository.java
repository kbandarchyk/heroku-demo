package vrp.herokudemo.repository;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vrp.herokudemo.repository.criteria.UserRepoCriteria;
import vrp.herokudemo.repository.dto.UserRepoDto;

public interface UserRepository {
    
    Flux<UserRepoDto> fetchListBy( UserRepoCriteria criteria, Pageable pageable );
    Mono<UserRepoDto> fetchBy( UserRepoCriteria criteria );
    Mono<Void> create(UserRepoDto repoDto);
    Mono<Void> update(UserRepoDto repoDto);
}
