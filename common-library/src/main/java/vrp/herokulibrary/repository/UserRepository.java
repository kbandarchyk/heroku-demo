package vrp.herokulibrary.repository;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vrp.herokulibrary.repository.criteria.UserRepoCriteria;
import vrp.herokulibrary.repository.dto.UserRepoDto;

public interface UserRepository {
    
    Flux<UserRepoDto> fetchListBy( UserRepoCriteria criteria, Pageable pageable );
    Mono<UserRepoDto> fetchBy( UserRepoCriteria criteria );
    Mono<Void> create(UserRepoDto repoDto);
    Mono<Void> update(UserRepoDto repoDto);
}
