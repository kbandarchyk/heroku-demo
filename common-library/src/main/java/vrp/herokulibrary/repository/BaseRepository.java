package vrp.herokulibrary.repository;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vrp.herokulibrary.exception.R2DBCException;
import vrp.herokulibrary.repository.criteria.RepoCriteria;
import vrp.herokulibrary.repository.dto.RepoDto;

public abstract class BaseRepository<T extends RepoDto, V extends RepoCriteria> {
    
    private DatabaseClient databaseClient;
    
    public BaseRepository( final ConnectionFactory connectionFactory ) {
        this.databaseClient = DatabaseClient.builder()
                                            .connectionFactory( connectionFactory )
                                            .build();
    }
    
    public abstract Class<T> getRepoDtoClass();
    
    public Flux<T> fetchListBy ( final V criteria, final Pageable pageable ){
        return databaseClient.select()
                             .from( getRepoDtoClass() )
                             .page( pageable )
                             .matching( criteria.constructCriteria() )
                             .fetch()
                             .all()
                             .doOnError( obj -> new R2DBCException( obj.getMessage(), obj.getCause() ) );
    }
    
    public Mono<T> fetchBy( final V criteria ){
        return databaseClient.select()
                             .from( getRepoDtoClass() )
                             .matching( criteria.constructCriteria() )
                             .fetch()
                             .first()
                             .doOnError( obj -> new R2DBCException( obj.getMessage(), obj.getCause() ) );
    }
    
    public Mono<Void> create( final T repoDto ) {
        return databaseClient.insert()
                             .into( getRepoDtoClass() )
                             .using( repoDto )
                             .then()
                             .doOnError( obj -> new R2DBCException( obj.getMessage(), obj.getCause() ) );
    }
    
    public Mono<Void> update( final T repoDto ) {
        return databaseClient.update()
                             .table( getRepoDtoClass() )
                             .using( repoDto )
                             .then()
                             .doOnError( obj -> new R2DBCException( obj.getMessage(), obj.getCause() ) );
    }
    
}
