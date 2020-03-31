package vrp.herokuweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@ComponentScan("vrp")
@PropertySource({"classpath:/profile.properties", "classpath:/application.properties"})
@EnableWebFlux
public class HerokuWebApplication {
    
    public static void main( String [] args ){
        SpringApplication.run( HerokuWebApplication.class, args );
    }
    
}
