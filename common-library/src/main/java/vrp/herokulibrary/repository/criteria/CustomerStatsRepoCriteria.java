package vrp.herokulibrary.repository.criteria;

import lombok.Builder;
import org.springframework.data.r2dbc.query.Criteria;
import vrp.herokulibrary.repository.fields.CustomerFields;
import vrp.herokulibrary.repository.fields.CustomerStatsFields;

import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@Builder( toBuilder = true )
public class CustomerStatsRepoCriteria implements RepoCriteria {

    private final List<Long> ids;
    private final Integer numberOfCustomersMoreThan;
    
    public CustomerStatsRepoCriteria( final List<Long> ids
                                    , final Integer numberOfCustomersMoreThan ) {
        this.ids = ids;
        this.numberOfCustomersMoreThan = numberOfCustomersMoreThan;
    }
    
    @Override
    public Criteria constructCriteria() {
        
        Criteria criteria = isEmpty( ids ) ? Criteria.where( CustomerFields.ID ).isNotNull()
                                           : Criteria.where( CustomerFields.ID ).in( ids );
        
        if( numberOfCustomersMoreThan != null ){
            criteria = criteria.and( CustomerStatsFields.NUMBER_OF_CUSTOMERS ).greaterThan( numberOfCustomersMoreThan );
        }
      
        return criteria;
    }
    
    @Override
    public String toString() {
        return String.format(
                "CustomerStatsRepoCriteria [ids=%s, numberOfCustomersMoreThan=%s]"
                , ids, numberOfCustomersMoreThan);
    }
}
