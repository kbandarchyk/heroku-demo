package vrp.herokulibrary.repository.dto;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import vrp.herokulibrary.repository.fields.CustomerStatsFields;

import java.util.Date;

@Table("customer_stats")
public class CustomerStatsRepoDto implements RepoDto {
    
    @Id
    @Column( CustomerStatsFields.ID )
    private final Long id;
    
    @Column( CustomerStatsFields.NUMBER_OF_CUSTOMERS )
    private final int numberOfCustomers;
    
    @Column( CustomerStatsFields.CREATED_DATE )
    private final Date createdDate;
    
    public CustomerStatsRepoDto( final Long id
                               , final int numberOfCustomers
                               , final Date createdDate ) {
        this.id = id;
        this.numberOfCustomers = numberOfCustomers;
        this.createdDate = createdDate;
    }
    
    public Long getId() {
        return id;
    }
    
    public int getNumberOfCustomers() {
        return numberOfCustomers;
    }
    
    public Date getCreatedDate() {
        return createdDate;
    }
    
    @Override
    public String toString() {
        return String.format(
                "CustomerStatsRepoDto [id=%s, numberOfCustomers=%s, createdDate=%s]"
                , id, numberOfCustomers, createdDate);
    }
}
