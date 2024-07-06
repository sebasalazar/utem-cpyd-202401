package cl.utem.ws.exception;

public class AuthException extends RuntimeException {

    public AuthException() {
        super("NO fue posible autenticar");
    }

    public AuthException(String message) {
        super(message);
    }

    public AuthException(String message, Throwable cause) {
        super(message, cause);
    }
}
