package cl.utem.ws.api;

import cl.utem.ws.api.vo.ErrorVO;
import cl.utem.ws.exception.UtemException;
import jakarta.security.auth.message.AuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler({AuthException.class})
    public ResponseEntity<ErrorVO> handleException(AuthException a) {
        LOGGER.error("Error de autenticación: {}", a.getLocalizedMessage());
        LOGGER.debug("Error de autenticación: {}", a.getMessage(), a);

        return new ResponseEntity<>(new ErrorVO(-17, a.getLocalizedMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({UtemException.class})
    public ResponseEntity<ErrorVO> handleException(UtemException u) {
        LOGGER.error("Error manejado: {}", u.getLocalizedMessage());
        LOGGER.debug("Error manejado: {}", u.getMessage(), u);

        return new ResponseEntity<>(new ErrorVO(-1, u.getLocalizedMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorVO> handleException(Exception e) {
        LOGGER.error("Error NO manejado: {}", e.getLocalizedMessage());
        LOGGER.debug("Error NO manejado: {}", e.getMessage(), e);

        return new ResponseEntity<>(new ErrorVO(-999, e.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
