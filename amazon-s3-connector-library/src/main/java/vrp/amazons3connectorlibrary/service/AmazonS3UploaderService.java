package vrp.amazons3connectorlibrary.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import vrp.amazons3connectorlibrary.exception.AmazonS3UploadObjectException;

import static vrp.amazons3connectorlibrary.utils.FileDirUtils.constructFilePath;

@Service
public class AmazonS3UploaderService {
    
    private final Logger logger = LoggerFactory.getLogger( AmazonS3UploaderService.class );
    
    private S3AsyncClient amazonS3Client;
    private String bucket;
    
    @Autowired
    public AmazonS3UploaderService( final S3AsyncClient amazonS3Client
                                  , final @Value( "${amazon.s3.bucket-name}" ) String bucket ) {
        this.amazonS3Client = amazonS3Client;
        this.bucket = bucket;
    }
    
    public Mono<Void> uploadFile( final FilePart filePart
                                , final String fileDir ) {
        
        logger.debug( "Calling uploadFile with params: fileName: {}, fileDir: {}", filePart.filename(), fileDir );
    
        return DataBufferUtils.join( filePart.content() )
                              .map( DataBuffer::asByteBuffer )
                              .map( byteBuffer -> amazonS3Client.putObject( constructPutObjectRequest( filePart, fileDir )
                                                                          , AsyncRequestBody.fromByteBuffer( byteBuffer ) ) )
                              .flatMap( Mono::fromFuture )
                              .map( this::checkResponse )
                              .then();
    }
    
    private PutObjectRequest constructPutObjectRequest( final FilePart file
                                                      , final String fileDir ) {
        
        return PutObjectRequest.builder()
                               .bucket( bucket )
                               .key( constructFilePath( file.filename(), fileDir ) )
                               .build();
    }
    
    
    private PutObjectResponse checkResponse( final PutObjectResponse response ) {
    
        final var sdkHttpResponse = response.sdkHttpResponse();
        
        if( !response.sdkHttpResponse().isSuccessful() ){
            throw new AmazonS3UploadObjectException( sdkHttpResponse.statusCode()
                                                   , sdkHttpResponse.statusText().orElse( null ) );
        }
        
        return response;
    }
    
}
