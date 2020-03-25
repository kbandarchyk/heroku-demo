package vrp.herokudemo.repository.criteria;

import lombok.Builder;
import org.springframework.data.r2dbc.query.Criteria;
import vrp.herokudemo.repository.fields.UsersFields;

import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@Builder( toBuilder = true )
public class UserRepoCriteria implements RepoCriteria {

    private final List<Long> ids;
    private final String login;
    private final Boolean isDeleted;
    
    private UserRepoCriteria( final List<Long> ids
                            , final String login
                            , final Boolean isDeleted ) {
        this.ids = ids;
        this.login = login;
        this.isDeleted = isDeleted;

    }
    
    @Override
    public Criteria constructCriteria() {
        
        Criteria criteria = isEmpty( ids ) ? Criteria.where( UsersFields.ID ).isNotNull()
                                           : Criteria.where( UsersFields.ID ).in( ids );
        
        if( login != null ){
            criteria = criteria.and( UsersFields.LOGIN ).is( login );
        }
        
        if( isDeleted != null ){
            criteria = criteria.and( UsersFields.IS_DELETED ).is( isDeleted );
        }
        
        return criteria;
    }
    
    @Override
    public String toString() {
        return String.format(
                "UserRepoCriteria [ids=%s, login=%s]"
                , ids, login);
    }
}
