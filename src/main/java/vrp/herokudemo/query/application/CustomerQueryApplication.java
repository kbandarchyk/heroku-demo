package vrp.herokudemo.query.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vrp.herokudemo.query.controller.criteria.CustomerQueryCriteria;
import vrp.herokudemo.query.dto.CustomerQueryDto;
import vrp.herokudemo.query.service.CustomerQueryService;

@Service
public class CustomerQueryApplication {
    
    private CustomerQueryService customerQueryService;
    
    @Autowired
    public CustomerQueryApplication( final CustomerQueryService customerQueryService ) {
        this.customerQueryService = customerQueryService;
    }
    
    public Flux<CustomerQueryDto> fetchAll() {
        return customerQueryService.fetchAll();
    }
    
    public Flux<CustomerQueryDto> fetchListBy( final CustomerQueryCriteria queryCriteria ){
        return customerQueryService.fetchListBy( queryCriteria );
    }
    
    public Mono<CustomerQueryDto> fetchById( final Long customerId ) {
        return customerQueryService.fetchById( customerId );
    }
}
