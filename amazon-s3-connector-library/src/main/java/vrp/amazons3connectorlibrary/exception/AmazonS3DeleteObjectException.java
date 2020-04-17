package vrp.amazons3connectorlibrary.exception;

public class AmazonS3DeleteObjectException extends RuntimeException {
    
    private final int statusCode;
    
    public AmazonS3DeleteObjectException( final int statusCode
                                        , final String message ) {
        super( message );
        this.statusCode = statusCode;
    }
    
}
