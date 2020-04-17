package vrp.amazons3connectorlibrary.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;
import software.amazon.awssdk.services.s3.model.S3Object;
import vrp.amazons3connectorlibrary.dto.S3FileDto;

@Service
public class AmazonS3FileInformationService {
    
    private final Logger logger = LoggerFactory.getLogger( AmazonS3FileInformationService.class );
    
    private S3AsyncClient amazonS3Client;
    private String bucket;
    
    @Autowired
    public AmazonS3FileInformationService( final S3AsyncClient amazonS3Client
                                         , final @Value( "${amazon.s3.bucket-name}" ) String bucket ) {
        this.amazonS3Client = amazonS3Client;
        this.bucket = bucket;
    }
    
    public Flux<S3FileDto> fetchAllFilesByBucketDir( final String fileDir ) {
    
        logger.debug( "Calling fetchAllFilesByBucketDir with params: {}", fileDir );
        
        return Mono.fromFuture( amazonS3Client.listObjects( ListObjectsRequest.builder()
                                                                              .bucket( bucket )
                                                                              .prefix( fileDir )
                                                                              .build() ) )
                   .flatMapIterable( ListObjectsResponse::contents )
                   .map( this::toS3FileDto );
    }
    
    ////////////////
    // Util
    ////
    
    protected S3FileDto toS3FileDto(final S3Object s3Object ) {
        
        return new S3FileDto( s3Object.key()
                            , s3Object.size()
                            , s3Object.eTag()
                            , s3Object.lastModified() );
    }
    
}
