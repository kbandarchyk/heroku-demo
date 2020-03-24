package vrp.herokudemo.repository.impl;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.stereotype.Repository;
import vrp.herokudemo.repository.BaseRepository;
import vrp.herokudemo.repository.CustomerRepository;
import vrp.herokudemo.repository.criteria.CustomerRepoCriteria;
import vrp.herokudemo.repository.dto.CustomerRepoDto;

@Repository
public class CustomerRepositoryImpl extends BaseRepository<CustomerRepoDto, CustomerRepoCriteria> implements CustomerRepository {
    
    public CustomerRepositoryImpl( final ConnectionFactory connectionFactory ) {
        super( connectionFactory );
    }
    
    @Override
    public Class<CustomerRepoDto> getRepoDtoClass() {
        return CustomerRepoDto.class;
    }
}
