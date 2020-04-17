package vrp.amazons3connectorlibrary.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectResponse;
import vrp.amazons3connectorlibrary.exception.AmazonS3DeleteObjectException;

import static vrp.amazons3connectorlibrary.utils.FileDirUtils.constructFilePath;

@Service
public class AmazonS3DeletionService {
    
    private final Logger logger = LoggerFactory.getLogger( AmazonS3DeletionService.class );
    
    private S3AsyncClient amazonS3Client;
    private String bucket;
    
    @Autowired
    public AmazonS3DeletionService( final S3AsyncClient amazonS3Client
                                  , final @Value( "${amazon.s3.bucket-name}" ) String bucket ) {
        this.amazonS3Client = amazonS3Client;
        this.bucket = bucket;
    }
    
    public Mono<Void> deleteFile( final String fileName
                                , final String fileDir ) {
    
        logger.debug( "Calling deleteFile with params: fileName: {}, fileDir: {}", fileName, fileDir );
        
        return Mono.fromFuture( amazonS3Client.deleteObject( constructDeleteObjectRequest( fileName, fileDir ) ) )
                   .map( this::checkResponse )
                   .then();
    }
    
    private DeleteObjectRequest constructDeleteObjectRequest( final String fileName
                                                            , final String fileDir ) {
        
        return DeleteObjectRequest.builder()
                                  .bucket( bucket )
                                  .key( constructFilePath( fileName, fileDir ) )
                                  .build();
    }
    
    private DeleteObjectResponse checkResponse( final DeleteObjectResponse response ) {
    
        final var sdkHttpResponse = response.sdkHttpResponse();
        
        if( !sdkHttpResponse.isSuccessful() ){
            throw new AmazonS3DeleteObjectException( sdkHttpResponse.statusCode()
                                                   , sdkHttpResponse.statusText().orElse( null ) );
        }
        
        return response;
        
    }
}
