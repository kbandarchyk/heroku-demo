package vrp.herokuweb.command.controller;

import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import vrp.amazons3connectorlibrary.service.AmazonS3DeletionService;
import vrp.amazons3connectorlibrary.service.AmazonS3UploaderService;

@RestController
@RequestMapping( "/api/v1.0/file" )
public class FileCommandController {
    
    private AmazonS3UploaderService amazonS3UploaderService;
    private AmazonS3DeletionService amazonS3DeletionService;
    
    public FileCommandController( final AmazonS3UploaderService amazonS3UploaderService
                                , final AmazonS3DeletionService amazonS3DeletionService ) {
        this.amazonS3UploaderService = amazonS3UploaderService;
        this.amazonS3DeletionService = amazonS3DeletionService;
    }
    
    @PostMapping( value = "/upload" )
    public Mono<Void> uploadFile( final @RequestPart FilePart filePart
                                , final @RequestParam( value = "dirPath", required = false ) String dirPath ) {
        return amazonS3UploaderService.uploadFile( filePart, dirPath );
    }

    @PostMapping( value = "/delete" )
    public Mono<Void> deleteFile( @RequestParam( "fileName" ) final String fileName
                                , @RequestParam( value = "dirPath", required = false ) final String dirPath ){
    
        return amazonS3DeletionService.deleteFile( fileName, dirPath );
    }
    
}
