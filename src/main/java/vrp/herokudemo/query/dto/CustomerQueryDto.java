package vrp.herokudemo.query.dto;

import java.util.Date;

public class CustomerQueryDto {
    
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final Date createdDate;
    
    public CustomerQueryDto( final Long id
                           , final String firstName
                           , final String lastName
                           , final String phoneNumber
                           , final Date createdDate ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.createdDate = createdDate;
    }
    
    public Long getId() {
        return id;
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
    
    public Date getCreatedDate() {
        return createdDate;
    }
    
    @Override
    public String toString() {
        return String.format(
                "CustomerQueryDto [id=%s, firstName=%s, lastName=%s, phoneNumber=%s, createdDate=%s]"
                , id, firstName, lastName, phoneNumber, createdDate);
    }
}
