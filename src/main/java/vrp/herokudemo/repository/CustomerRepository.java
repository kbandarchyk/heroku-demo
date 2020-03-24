package vrp.herokudemo.repository;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vrp.herokudemo.repository.criteria.CustomerRepoCriteria;
import vrp.herokudemo.repository.dto.CustomerRepoDto;

public interface CustomerRepository {
    
    Flux<CustomerRepoDto> fetchListBy(CustomerRepoCriteria criteria, Pageable pageable );
    Mono<CustomerRepoDto> fetchBy( CustomerRepoCriteria criteria );
    Mono<Void> create( CustomerRepoDto repoDto );
    Mono<Void> update( CustomerRepoDto repoDto );
}
