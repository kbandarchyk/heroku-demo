package vrp.herokudemo.command.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import vrp.herokudemo.command.domain.Customer;
import vrp.herokudemo.command.domain.CustomerFactory;
import vrp.herokudemo.command.domain.values.ID;
import vrp.herokudemo.command.dto.CustomerInputDto;
import vrp.herokudemo.exception.NotFoundException;

@Component
public class CustomerCommandApplication {
    
    private static final String CUSTOMER_NOT_FOUND_BY_ID = "Customer not found by id {0}";

    private final CustomerFactory customerFactory;
    
    @Autowired
    public CustomerCommandApplication( final CustomerFactory customerFactory ) {
        this.customerFactory = customerFactory;
    }
    
    public Mono<Void> createCustomer( final CustomerInputDto inputDto ){
        return customerFactory.create( customerFactory.constructNew( inputDto ) );
    }
    
    public Mono<Void> updateCustomer( final ID customerId
                                    , final CustomerInputDto inputDto ){
        return customerFactory.fetchBy( customerId )
                              .switchIfEmpty( Mono.error( new NotFoundException( CUSTOMER_NOT_FOUND_BY_ID, customerId.getValue() ) ) )
                              .map( customer -> customer.updateFrom( inputDto ) )
                              .flatMap( customerFactory::update )
                              .then();
    }
    
    public Mono<Void> deleteCustomer( final ID customerId ){
        return customerFactory.fetchBy( customerId )
                              .switchIfEmpty( Mono.error( new NotFoundException( CUSTOMER_NOT_FOUND_BY_ID, customerId.getValue() ) ) )
                              .map( Customer::markAsDeleted )
                              .flatMap( customerFactory::update )
                              .then();
    }
}
