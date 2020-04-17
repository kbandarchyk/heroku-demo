package vrp.amazons3connectorlibrary.config;

import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;

public class AmazonS3CredentialProvider implements AwsCredentialsProvider {

    private final String accessKey;
    private final String secretAccessKey;
    
    public AmazonS3CredentialProvider( final String accessKey
                                     , final String secretAccessKey ) {
        this.accessKey = accessKey;
        this.secretAccessKey = secretAccessKey;
    }
    
    @Override
    public AwsCredentials resolveCredentials() {
        
        return new AwsCredentials() {
            @Override
            public String accessKeyId() {
                return accessKey;
            }
    
            @Override
            public String secretAccessKey() {
                return secretAccessKey;
            }
        };
    }
}
