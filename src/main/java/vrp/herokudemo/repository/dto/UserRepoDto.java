package vrp.herokudemo.repository.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import vrp.herokudemo.repository.fields.UsersFields;

@Table( "users" )
public class UserRepoDto implements RepoDto {
    
    @Id
    @Column( UsersFields.ID )
    private final Long id;
    
    @Column( UsersFields.FIRST_NAME )
    private final String firstName;
    
    @Column( UsersFields.LAST_NAME )
    private final String lastName;
    
    @Column( UsersFields.LOGIN )
    private final String login;
    
    @Column( UsersFields.PASSWORD )
    private final String password;
    
    @Column( UsersFields.IS_DELETED )
    private final boolean isDeleted;
    
    public UserRepoDto( final Long id
                      , final String firstName
                      , final String lastName
                      , final String login
                      , final String password
                      , final boolean isDeleted ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
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
    
    public String getLogin() {
        return login;
    }
    
    public String getPassword() {
        return password;
    }
    
    public boolean isDeleted() {
        return isDeleted;
    }
    
    @Override
    public String toString() {
        return String.format(
                "UserRepoDto [id=%s, firstName=%s, lastName=%s, login=%s, password=%s, isDeleted=%s]"
                , id, firstName, lastName, login, password, isDeleted);
    }
}
