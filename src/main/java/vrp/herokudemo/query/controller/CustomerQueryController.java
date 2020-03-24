package vrp.herokudemo.query.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vrp.herokudemo.exception.NotFoundException;
import vrp.herokudemo.query.application.CustomerQueryApplication;
import vrp.herokudemo.query.controller.criteria.CustomerQueryCriteria;
import vrp.herokudemo.query.dto.CustomerQueryDto;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping( "/api/v1.0/customer" )
public class CustomerQueryController {
    
    private final CustomerQueryApplication customerQueryApplication;
    
    public CustomerQueryController(final CustomerQueryApplication customerQueryApplication) {
        this.customerQueryApplication = customerQueryApplication;
    }
    
    @PostMapping( "/fetchAll" )
    public Flux<CustomerQueryDto> fetchAll() {
        return customerQueryApplication.fetchAll();
    }
    
    
    @PostMapping( "/fetchListBy" )
    public Flux<CustomerQueryDto> fetchListBy( final @RequestBody CustomerQueryCriteria queryCriteria ) {
        return customerQueryApplication.fetchListBy( queryCriteria );
    }
    
    @PostMapping( "{id}/fetchBy")
    public Mono<CustomerQueryDto> fetchById( final @PathVariable("id") Long customerId ) {
        return customerQueryApplication.fetchById( customerId );
    }
    
    @ExceptionHandler({ NotFoundException.class } )
    public void handleDefineException( final RuntimeException e
            , final HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, e.getMessage());
    }
    
    @ResponseStatus( code = HttpStatus.INTERNAL_SERVER_ERROR
            , reason = "An exception occurred. See log for more information")
    @ExceptionHandler({RuntimeException.class})
    public void handleUnexpectedException(final RuntimeException e) {
    }
    
}
