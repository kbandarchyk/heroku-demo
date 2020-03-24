package vrp.herokudemo.command.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import vrp.herokudemo.command.application.CustomerCommandApplication;
import vrp.herokudemo.command.domain.values.ID;
import vrp.herokudemo.command.dto.CustomerInputDto;
import vrp.herokudemo.exception.CreateInvalidObjectException;
import vrp.herokudemo.exception.NotFoundException;
import vrp.herokudemo.exception.R2DBCException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping( "/api/v1.0/customer" )
public class CustomerCommandController {
    
    private final CustomerCommandApplication customerCommandApplication;
    
    public CustomerCommandController( final CustomerCommandApplication customerCommandApplication ) {
        this.customerCommandApplication = customerCommandApplication;
    }
    
    @PostMapping( "/create" )
    public Mono<Void> create( final @RequestBody CustomerInputDto inputDto ) {
        return customerCommandApplication.createCustomer( inputDto );
    }
    
    
    @PostMapping( "/{id}/update" )
    public Mono<Void> update( final @PathVariable("id") Long id
                            , final @RequestBody CustomerInputDto inputDto ) {
        return customerCommandApplication.updateCustomer( new ID( id ), inputDto );
    }
    
    @PostMapping( "/{id}/delete")
    public Mono<Void> delete( final @PathVariable("id") Long id ) {
        return customerCommandApplication.deleteCustomer( new ID( id ) );
    }
    
    @ExceptionHandler({ NotFoundException.class
                      , CreateInvalidObjectException.class
                      , R2DBCException.class } )
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
