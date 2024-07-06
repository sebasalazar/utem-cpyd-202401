package cl.utem.ws.api.v1;

import cl.utem.ws.api.vo.InfoVO;
import cl.utem.ws.domain.model.Credential;
import cl.utem.ws.exception.AuthException;
import cl.utem.ws.exception.UtemException;
import cl.utem.ws.manager.AuthManager;
import cl.utem.ws.manager.UtemManager;
import jakarta.servlet.http.HttpServletRequest;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value = "/v1/utem",
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class UtemRest implements Serializable {

    private static final long serialVersionUID = 1L;

    private final transient AuthManager authManager;
    private final transient UtemManager utemManager;
    private static final Logger LOGGER = LoggerFactory.getLogger(UtemRest.class);

    @Autowired
    public UtemRest(AuthManager authManager, UtemManager utemManager) {
        this.authManager = authManager;
        this.utemManager = utemManager;
    }

    @GetMapping(value = "/distance/{facultyCode}/{myLatitude}/{myLongitud}/calculate")
    public ResponseEntity<InfoVO> calculateDistance(HttpServletRequest request,
            @RequestHeader("Authorization") String authorization,
            @PathVariable("facultyCode") String facultyCode,
            @PathVariable("myLatitude") Double myLatitude,
            @PathVariable("myLongitud") Double myLongitud) {
        Credential credential = authManager.authenticate(authorization);
        if (credential == null) {
            throw new AuthException();
        }
        authManager.markEntrance(credential, request.getHeader("UserAgent"), request.getRemoteAddr());

        InfoVO info = utemManager.calculate(facultyCode, myLatitude, myLongitud);
        if (info == null) {
            throw new UtemException();
        }

        return ResponseEntity.ok(info);
    }
}
