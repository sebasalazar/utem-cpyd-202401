package cl.utem.ws.api.v1;

import cl.utem.ws.api.vo.InfoVO;
import cl.utem.ws.api.vo.LoginVO;
import cl.utem.ws.exception.UtemException;
import cl.utem.ws.manager.AuthManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value = "/v1/auth",
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class AuthRest {

    private final transient AuthManager authManager;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthRest.class);

    @Autowired
    public AuthRest(AuthManager authManager) {
        this.authManager = authManager;
    }

    @PostMapping(value = "/login",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<InfoVO> login(@RequestBody LoginVO body) {
        InfoVO info = authManager.getJwt(body);
        if (info == null) {
            LOGGER.error("Los datos de entrada ({}) NO fueron encontrados", body);
            throw new UtemException("Datos de autenticación inválidos");
        }

        return ResponseEntity.ok(info);
    }
}
