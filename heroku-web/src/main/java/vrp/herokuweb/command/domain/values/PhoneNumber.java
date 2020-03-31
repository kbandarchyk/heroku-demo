package vrp.herokuweb.command.domain.values;

public class PhoneNumber {
    
    private final String value;
    
    public PhoneNumber(final String value ) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return String.format(
                "PhoneNumber [value=%s]"
                , value);
    }
}
