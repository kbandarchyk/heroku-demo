package vrp.herokudemo.command.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerInputDto {
    
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    
    @JsonCreator
    public CustomerInputDto( final @JsonProperty( "firstName" ) String firstName
                           , final @JsonProperty( "lastName" ) String lastName
                           , final @JsonProperty( "phoneNumber" ) String phoneNumber ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    @Override
    public String toString() {
        return String.format(
                "CustomerInputDto [firstName=%s, lastName=%s, phoneNumber=%s]"
                , firstName, lastName, phoneNumber);
    }
}
