package vrp.herokuweb.query.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import vrp.amazons3connectorlibrary.dto.S3FileDto;
import vrp.amazons3connectorlibrary.service.AmazonS3DownloaderService;
import vrp.amazons3connectorlibrary.service.AmazonS3FileInformationService;

@RestController
@RequestMapping( "/api/v1.0/file" )
public class FileQueryController {
    
    private final AmazonS3FileInformationService amazonS3FileInformationService;
    private final AmazonS3DownloaderService amazonS3DownloaderService;
    
    @Autowired
    public FileQueryController( final AmazonS3FileInformationService amazonS3FileInformationService
                              , final AmazonS3DownloaderService amazonS3DownloaderService ) {
        this.amazonS3FileInformationService = amazonS3FileInformationService;
        this.amazonS3DownloaderService = amazonS3DownloaderService;
    }
    
    @PostMapping( "/fetchList" )
    public Flux<S3FileDto> fetchList( @RequestParam( value = "dirPath",required = false ) final String dirPath ){
        return amazonS3FileInformationService.fetchAllFilesByBucketDir( dirPath );
    }
    
    @PostMapping( "/downloadFile" )
    public Mono<ResponseEntity<InputStreamResource>> downloadFile( @RequestParam( "fileName" ) final String fileName
                                                                 , @RequestParam( value = "dirPath", required = false ) final String dirPath ){
        
        return amazonS3DownloaderService.downloadFile( fileName, dirPath )
                                        .map( is -> ResponseEntity.ok()
                                                                  .header( HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName )
                                                                  .body( new InputStreamResource( is ) ) );
    }
    
}
