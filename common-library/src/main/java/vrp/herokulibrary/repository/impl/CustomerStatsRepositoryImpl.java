package vrp.herokulibrary.repository.impl;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.stereotype.Repository;
import vrp.herokulibrary.repository.BaseRepository;
import vrp.herokulibrary.repository.CustomerStatsRepository;
import vrp.herokulibrary.repository.criteria.CustomerStatsRepoCriteria;
import vrp.herokulibrary.repository.dto.CustomerStatsRepoDto;

@Repository
public class CustomerStatsRepositoryImpl extends BaseRepository<CustomerStatsRepoDto, CustomerStatsRepoCriteria> implements CustomerStatsRepository {
    
    public CustomerStatsRepositoryImpl(final ConnectionFactory connectionFactory ) {
        super( connectionFactory );
    }
    
    @Override
    public Class<CustomerStatsRepoDto> getRepoDtoClass() {
        return CustomerStatsRepoDto.class;
    }
}
