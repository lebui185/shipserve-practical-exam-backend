package lebui.shipserve.practicaljavaexam.exception;

public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = -3810732313133161373L;

    public NotFoundException() {
        
    }
    
    public NotFoundException(String message) {
        super(message);
    }
    
}
