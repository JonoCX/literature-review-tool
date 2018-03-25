package uk.ac.manchester.cs.iam.litreviewtool.models;

/**
 * @author Jonathan Carlton
 */
public class InputInterruptException extends Exception {

    public InputInterruptException() { super(); }
    public InputInterruptException(String message) { super(message); }
    public InputInterruptException(String message, Throwable cause) { super(message, cause); }
    public InputInterruptException(Throwable cause) { super(cause); }

}
