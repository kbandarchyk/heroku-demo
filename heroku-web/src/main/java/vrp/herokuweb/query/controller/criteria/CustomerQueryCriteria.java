package vrp.herokuweb.query.controller.criteria;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerQueryCriteria {
    
    private final String firstName;
    private final String lastName;
    
    @JsonCreator
    public CustomerQueryCriteria( final @JsonProperty( "firstName" ) String firstName
                                , final @JsonProperty( "lastName" ) String lastName ) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    @Override
    public String toString() {
        return String.format(
                "CustomerQueryCriteria [firstName=%s, lastName=%s]"
                , firstName, lastName);
    }
}
