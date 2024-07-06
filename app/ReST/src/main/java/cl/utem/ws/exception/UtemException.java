package cl.utem.ws.exception;

public class UtemException extends RuntimeException {

    public UtemException() {
        super("Ha ocurrido un error inesperado");
    }

    public UtemException(String message) {
        super(message);
    }

    public UtemException(String message, Throwable cause) {
        super(message, cause);
    }
}
