package vrp.herokuweb.command.domain;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import vrp.herokuweb.command.domain.values.ID;
import vrp.herokuweb.command.domain.values.PhoneNumber;
import vrp.herokuweb.command.dto.CustomerInputDto;
import vrp.herokulibrary.repository.CustomerRepository;
import vrp.herokulibrary.repository.criteria.CustomerRepoCriteria;
import vrp.herokulibrary.repository.dto.CustomerRepoDto;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class CustomerFactory {

    private final CustomerRepository customerRepository;
    
    public CustomerFactory( final CustomerRepository customerRepository ) {
        this.customerRepository = customerRepository;
    }
    
    public Customer constructNew( final CustomerInputDto inputDto ){
        
        return new Customer( Optional.empty()
                           , inputDto.getFirstName()
                           , inputDto.getLastName()
                           , Optional.ofNullable( inputDto.getPhoneNumber() )
                                     .map( PhoneNumber::new )
                           , new Date()
                           , false );
    }
    
    public Mono<Customer> fetchBy(final ID id ){
        
        return customerRepository.fetchBy( CustomerRepoCriteria.builder()
                                                               .ids( List.of( id.getValue() ) )
                                                               .isDeleted( false )
                                                               .build() )
                                 .map( this::constructCustomer );
    }
    
    public Mono<Void> create( final Customer customer ){
        return customerRepository.create( toRepoDto( customer ) );
    }
    
    public Mono<Void> update( final Customer customer ){
        return customerRepository.update( toRepoDto( customer ) );
    }
    
    ////////////////
    // Util
    ////
    
    private Customer constructCustomer( final CustomerRepoDto repoDto ) {
        
        return new Customer( Optional.of( repoDto.getId() )
                                     .map( ID::new )
                           , repoDto.getFirstName()
                           , repoDto.getLastName()
                           , Optional.ofNullable( repoDto.getPhoneNumber() )
                                     .map( PhoneNumber::new )
                           , repoDto.getCreatedDate()
                           , repoDto.isDeleted() );
    }
    
    private CustomerRepoDto toRepoDto( final Customer customer ) {
        
        return new CustomerRepoDto( customer.id
                                            .map(ID::getValue)
                                            .orElse( null )
                                  , customer.firstName
                                  , customer.lastName
                                  , customer.phoneNumber
                                            .map( PhoneNumber::getValue )
                                            .orElse( null )
                                  , customer.createdDate
                                  , customer.isDeleted );
    }
    
}
