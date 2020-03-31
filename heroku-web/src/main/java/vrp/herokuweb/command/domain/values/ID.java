package vrp.herokuweb.command.domain.values;

public class ID {
    
    private final Long value;
    
    public ID( final Long value ) {
        this.value = value;
    }
    
    public Long getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return String.format(
                "ID [value=%s]"
                , value);
    }
}
