package vrp.amazons3connectorlibrary.exception;

public class AmazonS3UploadObjectException extends RuntimeException {
    
    private final int statusCode;
    
    public AmazonS3UploadObjectException(final int statusCode
                                     , final String message ) {
        super( message );
        this.statusCode = statusCode;
    }
}
