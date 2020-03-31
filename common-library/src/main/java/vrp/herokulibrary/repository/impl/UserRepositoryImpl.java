package vrp.herokulibrary.repository.impl;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.stereotype.Repository;
import vrp.herokulibrary.repository.BaseRepository;
import vrp.herokulibrary.repository.UserRepository;
import vrp.herokulibrary.repository.criteria.UserRepoCriteria;
import vrp.herokulibrary.repository.dto.UserRepoDto;

@Repository
public class UserRepositoryImpl extends BaseRepository<UserRepoDto, UserRepoCriteria> implements UserRepository {
    
    public UserRepositoryImpl(final ConnectionFactory connectionFactory ) {
        super( connectionFactory );
    }
    
    @Override
    public Class<UserRepoDto> getRepoDtoClass() {
        return UserRepoDto.class;
    }
}
