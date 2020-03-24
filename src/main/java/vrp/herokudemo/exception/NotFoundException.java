package vrp.herokudemo.exception;

import java.text.MessageFormat;

public class NotFoundException extends RuntimeException{
    
    public NotFoundException( final String message ) {
        super( message );
    }
    
    public NotFoundException( final String templateMessage
                            , final Object ... params) {
        super(MessageFormat.format( templateMessage, params));
    }
}
