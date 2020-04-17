package vrp.amazons3connectorlibrary.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.BytesWrapper;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.async.AsyncResponseTransformer;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import vrp.amazons3connectorlibrary.exception.AmazonS3DeleteObjectException;
import vrp.amazons3connectorlibrary.exception.AmazonS3DownloadObjectException;

import java.io.InputStream;

import static vrp.amazons3connectorlibrary.utils.FileDirUtils.constructFilePath;

@Service
public class AmazonS3DownloaderService {
    
    private final Logger logger = LoggerFactory.getLogger( AmazonS3DownloaderService.class );
    
    private S3AsyncClient amazonS3Client;
    private String bucket;
    
    @Autowired
    public AmazonS3DownloaderService( final S3AsyncClient amazonS3Client
                                    , final @Value( "${amazon.s3.bucket-name}" ) String bucket ) {
        this.amazonS3Client = amazonS3Client;
        this.bucket = bucket;
    }
    
    public Mono<InputStream> downloadFile( final String fileName
                                         , final String fileDir ) {
    
        logger.debug( "Calling downloadFile with params: fileName: {}, fileDir: {}", fileName, fileDir );
        
        try {
            return Mono.fromFuture( amazonS3Client.getObject(constructGetObjectRequest( fileName, fileDir )
                                  , AsyncResponseTransformer.toBytes() ) )
                       .map( this::checkResponse )
                       .map( BytesWrapper::asInputStream );
            
        } catch ( NoSuchKeyException e ){
            throw new AmazonS3DownloadObjectException( e.getMessage() );
        }
    }
    
    private GetObjectRequest constructGetObjectRequest( final String fileName
                                                      , final String fileDir ) {
        
        return GetObjectRequest.builder()
                               .bucket( bucket )
                               .key( constructFilePath( fileName, fileDir ) )
                               .build();
    }
    
    private ResponseBytes<GetObjectResponse> checkResponse( final ResponseBytes<GetObjectResponse> response ) {
        
        final var sdkHttpResponse = response.response().sdkHttpResponse();
    
        if( !sdkHttpResponse.isSuccessful() ){
            throw new AmazonS3DeleteObjectException( sdkHttpResponse.statusCode()
                                                   , sdkHttpResponse.statusText().orElse( null ) );
        }
    
        return response;
    
    
    }
    
    
}
