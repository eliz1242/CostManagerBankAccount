package mvvm;

/**
 * this class will represent all the error messages of the project.
 */
public class CostManagerException extends Exception {

    public CostManagerException(String message) {
        super(message);
    }

    public CostManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}
