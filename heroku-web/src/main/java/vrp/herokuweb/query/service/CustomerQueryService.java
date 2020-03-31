package vrp.herokuweb.query.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vrp.herokulibrary.exception.NotFoundException;
import vrp.herokulibrary.repository.CustomerRepository;
import vrp.herokulibrary.repository.criteria.CustomerRepoCriteria;
import vrp.herokulibrary.repository.dto.CustomerRepoDto;
import vrp.herokuweb.query.controller.criteria.CustomerQueryCriteria;
import vrp.herokuweb.query.dto.CustomerQueryDto;

import java.util.List;

@Service
public class CustomerQueryService {
    
    private static final String CUSTOMER_NOT_FOUND_BY_ID = "Customer not found by id {0}";

    private final CustomerRepository customerRepository;
    
    @Autowired
    public CustomerQueryService( final CustomerRepository customerRepository ) {
        this.customerRepository = customerRepository;
    }
    
    public Flux<CustomerQueryDto> fetchAll() {
        return customerRepository.fetchListBy( CustomerRepoCriteria.builder()
                                                                   .isDeleted( false )
                                                                   .build()
                                             , Pageable.unpaged() )
                                 .map( this::toDto );
    }
    
    public Flux<CustomerQueryDto> fetchListBy( final CustomerQueryCriteria queryCriteria ) {
        return customerRepository.fetchListBy( CustomerRepoCriteria.builder()
                                                                   .firstName( queryCriteria.getFirstName() )
                                                                   .lastName( queryCriteria.getLastName() )
                                                                   .build()
                                             , Pageable.unpaged() )
                                 .map( this::toDto );
    }
    
    public Mono<CustomerQueryDto> fetchById( final Long customerId ) {
        return customerRepository.fetchBy( CustomerRepoCriteria.builder()
                                                               .ids( List.of( customerId ) )
                                                               .isDeleted( false )
                                                               .build() )
                                 .map( this::toDto )
                                 .switchIfEmpty( Mono.error( new NotFoundException( CUSTOMER_NOT_FOUND_BY_ID, customerId ) ) );
    }
    
    ////////////////
    // Util
    ////
    
    private CustomerQueryDto toDto(CustomerRepoDto repoDto ){
        return new CustomerQueryDto( repoDto.getId()
                                   , repoDto.getFirstName()
                                   , repoDto.getLastName()
                                   , repoDto.getPhoneNumber()
                                   , repoDto.getCreatedDate() );
    }
    
}
