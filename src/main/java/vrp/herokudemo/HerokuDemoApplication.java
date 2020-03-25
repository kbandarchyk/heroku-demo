package vrp.herokudemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@PropertySource({"classpath:/profile.properties", "classpath:/application.properties"})
@EnableWebFlux
public class HerokuDemoApplication {
    
    public static void main( String [] args ){
        SpringApplication.run( HerokuDemoApplication.class, args );
    }
    
}
