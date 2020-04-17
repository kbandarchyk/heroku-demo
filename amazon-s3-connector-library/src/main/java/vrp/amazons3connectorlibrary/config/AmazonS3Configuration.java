package vrp.amazons3connectorlibrary.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Configuration;

@Configuration
public class AmazonS3Configuration {

    @Bean
    public AwsCredentialsProvider awsCredentialsProviderBean( final @Value("${amazon.s3.access-key}") String accessKey
                                                            , final @Value("${amazon.s3.secret-access-key}")String secretAccessKey) {
        return new AmazonS3CredentialProvider( accessKey, secretAccessKey );
    }
    
    @Bean
    public S3AsyncClient awsS3Client( final AwsCredentialsProvider credentialProvider
                                    , final @Value( "${amazon.s3.region}" ) String region ) {
    
        return S3AsyncClient.builder()
                            .credentialsProvider( credentialProvider )
                            .region( Region.of( region ) )
                            .serviceConfiguration( S3Configuration.builder()
                                                                  .checksumValidationEnabled(false)
                                                                  .chunkedEncodingEnabled(true)
                                                                  .build() )
                            .build();
    }

}
