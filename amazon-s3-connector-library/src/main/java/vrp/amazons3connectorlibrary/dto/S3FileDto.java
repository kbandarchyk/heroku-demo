package vrp.amazons3connectorlibrary.dto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

public class S3FileDto {
    
    private final String key;
    private final long size;
    private final String eTag;
    private final Instant lastModifiedDate;
    
    public S3FileDto( final String key
                    , final long size
                    , final String eTag
                    , final Instant lastModifiedDate ) {
        this.key = key;
        this.size = size;
        this.eTag = eTag;
        this.lastModifiedDate = lastModifiedDate;
    }
    
    public String getKey() {
        return key;
    }
    
    public long getSize() {
        return size;
    }
    
    public String geteTag() {
        return eTag;
    }
    
    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }
    
    @Override
    public String toString() {
        return String.format(
                "S3FileDto [key=%s, size=%s, eTag=%s, lastModifiedDate=%s]"
                , key, size, eTag, lastModifiedDate);
    }
}
