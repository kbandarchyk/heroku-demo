package vrp.herokulibrary.config;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@EnableR2dbcRepositories( basePackages  = "vrp.herokulibrary.repository")
public class R2DBCDatabaseConfig extends AbstractR2dbcConfiguration {
    
    private final String host;
    private final int port;
    private final String database;
    private final String username;
    private final String password;
    
    public R2DBCDatabaseConfig( final @Value( "${database.url}" ) String dataBaseURLString ) throws URISyntaxException {
        
        final URI dataBaseURL = new URI( dataBaseURLString );
        
        this.host = dataBaseURL.getHost();
        this.port = dataBaseURL.getPort();
        this.database = dataBaseURL.getPath().substring( 1 );
        this.username = dataBaseURL.getUserInfo().split(":")[0];
        this.password = dataBaseURL.getUserInfo().split(":")[1];
    }
    
    @Bean
    @Override
    public PostgresqlConnectionFactory connectionFactory() {
        return new PostgresqlConnectionFactory( PostgresqlConnectionConfiguration.builder()
                                                                                 .host( host )
                                                                                 .port( port )
                                                                                 .database( database )
                                                                                 .username( username )
                                                                                 .password( password )
                                                                                 .build() );
    }
}
