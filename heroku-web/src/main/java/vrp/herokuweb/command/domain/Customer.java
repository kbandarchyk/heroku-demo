package vrp.herokuweb.command.domain;

import vrp.herokuweb.command.domain.values.ID;
import vrp.herokuweb.command.domain.values.PhoneNumber;
import vrp.herokuweb.command.dto.CustomerInputDto;
import vrp.herokulibrary.exception.CreateInvalidObjectException;

import java.util.Date;
import java.util.Optional;

public class Customer {
    
    protected final Optional<ID> id;
    protected final String firstName;
    protected final String lastName;
    protected final Optional<PhoneNumber> phoneNumber;
    protected final Date createdDate;
    protected final boolean isDeleted;
    
    public Customer( final Optional<ID> id
                   , final String firstName
                   , final String lastName
                   , final Optional<PhoneNumber> phoneNumber
                   , final Date createdDate
                   , final boolean isDeleted ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.createdDate = createdDate;
        this.isDeleted = isDeleted;
        
        init();
    }
    
    public Customer updateFrom( final CustomerInputDto inputDto ){
        return new Customer( id
                           , inputDto.getFirstName()
                           , inputDto.getLastName()
                           , Optional.ofNullable( inputDto.getPhoneNumber() )
                                     .map( PhoneNumber::new )
                           , createdDate
                           , isDeleted );
    }
    
    public Customer markAsDeleted(){
        return new Customer( id, firstName, lastName, phoneNumber, createdDate, true );
    }
    
    private void init() {
    
        if( firstName == null ){
            throw new CreateInvalidObjectException( "Customer firstName cant be null" );
        }
        
        if( lastName == null ){
            throw new CreateInvalidObjectException( "Customer lastName cant be null" );
        }
    }
    
}
