package vrp.herokuworker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.reactive.config.EnableWebFlux;
import vrp.herokuworker.jobs.CalculatingCountOfCustomersJob;

@SpringBootApplication
@ComponentScan("vrp")
@PropertySource({"classpath:/profile.properties", "classpath:/application.properties"})
@EnableWebFlux
@EnableScheduling
public class HerokuWorkerApplication {
    
    private CalculatingCountOfCustomersJob calculatingCountOfCustomersJob;
    
    @Autowired
    public HerokuWorkerApplication( final CalculatingCountOfCustomersJob calculatingCountOfCustomersJob ) {
        this.calculatingCountOfCustomersJob = calculatingCountOfCustomersJob;
    }
    
    public static void main(String [] args ){
        SpringApplication.run( HerokuWorkerApplication.class, args );
    }
    
    @Scheduled( cron = "${scheduleCalculateCountOfCustomers}" )
    public void startReadyCourses() {
        calculatingCountOfCustomersJob.run();
    }
}
