package vrp.herokudemo.repository.impl;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.stereotype.Repository;
import vrp.herokudemo.repository.BaseRepository;
import vrp.herokudemo.repository.UserRepository;
import vrp.herokudemo.repository.criteria.UserRepoCriteria;
import vrp.herokudemo.repository.dto.UserRepoDto;

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
