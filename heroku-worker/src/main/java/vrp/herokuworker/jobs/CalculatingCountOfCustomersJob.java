package vrp.herokuworker.jobs;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vrp.herokulibrary.repository.CustomerRepository;
import vrp.herokulibrary.repository.CustomerStatsRepository;
import vrp.herokulibrary.repository.criteria.CustomerRepoCriteria;
import vrp.herokulibrary.repository.dto.CustomerStatsRepoDto;

import java.util.Date;

@Service
public class CalculatingCountOfCustomersJob implements Runnable {

    private CustomerRepository customerRepository;
    private CustomerStatsRepository customerStatsRepository;
    
    public CalculatingCountOfCustomersJob( final CustomerRepository customerRepository
                                         , final CustomerStatsRepository customerStatsRepository) {
        this.customerRepository = customerRepository;
        this.customerStatsRepository = customerStatsRepository;
    }
    
    @Override
    public void run() {
        
        customerRepository.fetchListBy( CustomerRepoCriteria.builder()
                                                            .isDeleted( false )
                                                            .build()
                                      , Pageable.unpaged() )
                          .count()
                          .map( numberOfCustomers -> toCustomerStatsRepoDto( numberOfCustomers.intValue() ) )
                          .flatMap( repoDto -> customerStatsRepository.create( repoDto ) )
                          .subscribe();
    }
    
    protected CustomerStatsRepoDto toCustomerStatsRepoDto( final int numberOfCustomers ) {
        
        return new CustomerStatsRepoDto( null
                                       , numberOfCustomers
                                       , new Date() );
    }
}
