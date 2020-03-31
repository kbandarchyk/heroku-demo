package vrp.herokulibrary.repository;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vrp.herokulibrary.repository.criteria.CustomerStatsRepoCriteria;
import vrp.herokulibrary.repository.dto.CustomerStatsRepoDto;

public interface CustomerStatsRepository {
    
    Flux<CustomerStatsRepoDto> fetchListBy(CustomerStatsRepoCriteria criteria, Pageable pageable);
    Mono<CustomerStatsRepoDto> fetchBy(CustomerStatsRepoCriteria criteria);
    Mono<Void> create(CustomerStatsRepoDto repoDto);
    Mono<Void> update(CustomerStatsRepoDto repoDto);
}
