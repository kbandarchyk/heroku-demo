package vrp.herokudemo.repository.dto;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import vrp.herokudemo.repository.fields.CustomerFields;

import java.util.Date;

@Table("customer")
public class CustomerRepoDto implements RepoDto {
    
    @Id
    @Column( CustomerFields.ID )
    private final Long id;
    
    @Column( CustomerFields.FIRST_NAME )
    private final String firstName;
    
    @Column( CustomerFields.LAST_NAME )
    private final String lastName;
    
    @Column( CustomerFields.PHONE_NUMBER )
    private final String phoneNumber;
    
    @Column( CustomerFields.CREATED_DATE )
    private final Date createdDate;
    
    @Column( CustomerFields.IS_DELETED )
    private final boolean isDeleted;
    
    
    public CustomerRepoDto( final Long id
                          , final String firstName
                          , final String lastName
                          , final String phoneNumber
                          , final Date createdDate
                          , final boolean isDeleted ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.createdDate = createdDate;
        this.isDeleted = isDeleted;
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
    
    public boolean isDeleted() {
        return isDeleted;
    }
    
    @Override
    public String toString() {
        return String.format(
                "CustomerRepoDto [id=%s, firstName=%s, lastName=%s, phoneNumber=%s, createdDate=%s, isDeleted=%s]"
                , id, firstName, lastName, phoneNumber, createdDate, isDeleted);
    }
}
