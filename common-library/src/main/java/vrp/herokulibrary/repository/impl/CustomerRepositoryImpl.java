package vrp.herokulibrary.repository.impl;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.stereotype.Repository;
import vrp.herokulibrary.repository.BaseRepository;
import vrp.herokulibrary.repository.CustomerRepository;
import vrp.herokulibrary.repository.criteria.CustomerRepoCriteria;
import vrp.herokulibrary.repository.dto.CustomerRepoDto;

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
