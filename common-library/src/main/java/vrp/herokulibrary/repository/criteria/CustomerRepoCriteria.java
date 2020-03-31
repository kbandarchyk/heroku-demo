package vrp.herokulibrary.repository.criteria;

import lombok.Builder;
import org.springframework.data.r2dbc.query.Criteria;
import vrp.herokulibrary.repository.fields.CustomerFields;

import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@Builder( toBuilder = true )
public class CustomerRepoCriteria implements RepoCriteria {

    private final List<Long> ids;
    private final String firstName;
    private final String lastName;
    private final Boolean isDeleted;
    
    private CustomerRepoCriteria( final List<Long> ids
                                , final String firstName
                                , final String lastName
                                , final Boolean isDeleted ) {
        this.ids = ids;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isDeleted = isDeleted;
    }
    
    @Override
    public Criteria constructCriteria() {
        
        Criteria criteria = isEmpty( ids ) ? Criteria.where( CustomerFields.ID ).isNotNull()
                                           : Criteria.where( CustomerFields.ID ).in( ids );
        
        if( firstName != null ){
            criteria = criteria.and( CustomerFields.FIRST_NAME ).like( firstName );
        }
        
        if( lastName != null ){
            criteria = criteria.and( CustomerFields.LAST_NAME ).like( lastName );
        }
        
        if( isDeleted != null ){
            criteria = criteria.and( CustomerFields.IS_DELETED ).is( isDeleted );
        }
        
        return criteria;
    }
    
    @Override
    public String toString() {
        return String.format(
                "CustomerRepoCriteria [firstName=%s, lastName=%s, isDeleted=%s]"
                , firstName, lastName, isDeleted);
    }
}
