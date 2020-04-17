package vrp.amazons3connectorlibrary.utils;

import java.util.Optional;

public final class FileDirUtils {
    
    private FileDirUtils(){
    }
    
    public static String constructFilePath( final String fileName, final String fileDir ) {
        return Optional.ofNullable( fileDir )
                       .map( obj -> obj + "/" + fileName )
                       .orElse( fileName );
    }
}
