package cl.utem.ws.manager;

import cl.utem.ws.api.vo.InfoVO;
import cl.utem.ws.api.vo.LoginVO;
import cl.utem.ws.domain.model.Credential;
import cl.utem.ws.domain.model.Entrance;
import cl.utem.ws.domain.repository.CredentialRepository;
import cl.utem.ws.domain.repository.EntranceRepository;
import cl.utem.ws.exception.AuthException;
import cl.utem.ws.util.AesUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthManager implements Serializable {

    private static final long serialVersionUID = 1L;

    private final transient CredentialRepository credentialRepository;
    private final transient EntranceRepository entranceRepository;

    @Autowired
    public AuthManager(CredentialRepository credentialRepository,
            EntranceRepository entranceRepository) {
        this.credentialRepository = credentialRepository;
        this.entranceRepository = entranceRepository;
    }

    public InfoVO getJwt(LoginVO request) {
        InfoVO response = null;
        if (request != null) {
            final String token = StringUtils.trimToEmpty(request.getToken());
            final String key = StringUtils.trimToEmpty(request.getKey());
            if (!StringUtils.isAnyBlank(token, key)) {
                Credential credential = credentialRepository.findByTokenAndKey(token, key);
                if (credential != null) {
                    final String code = credential.getCode();
                    final Instant now = Instant.now();
                    final String message = AesUtils.encrypt(String.format("%s;%d", code, now.getEpochSecond()));

                    final Algorithm algorithm = Algorithm.HMAC384(AesUtils.UTEM_KEY);
                    final String jwt = JWT.create()
                            .withJWTId(UUID.randomUUID().toString())
                            .withIssuer("utem.cl")
                            .withIssuedAt(now)
                            .withExpiresAt(now.plusSeconds(3717))
                            .withClaim("data", message)
                            .sign(algorithm);

                    response = new InfoVO(jwt);
                }
            }
        }
        return response;
    }

    public Credential authenticate(final String authorization) {
        Credential credential = null;
        try {
            if (StringUtils.isNotBlank(authorization)) {
                final String jwt = StringUtils.trimToEmpty(StringUtils.removeStartIgnoreCase(authorization, "Bearer"));

                final Algorithm algorithm = Algorithm.HMAC384(AesUtils.UTEM_KEY);
                JWTVerifier verifier = JWT.require(algorithm).withIssuer("utem.cl").build();
                DecodedJWT decoded = verifier.verify(jwt);
                Claim claim = decoded.getClaim("data");
                final String decrypt = AesUtils.decrypt(claim.asString());
                final String code = StringUtils.trimToEmpty(StringUtils.split(decrypt, ";")[0]);
                credential = credentialRepository.findByCodeIgnoreCase(code);
            }
        } catch (Exception e) {
            throw new AuthException("No fue posible autenticar", e);
        }
        return credential;
    }

    @Transactional
    public void markEntrance(final Credential credential, final String userAgent, final String ip) {
        if (credential != null && !StringUtils.isAnyBlank(userAgent, ip)) {
            Entrance entrance = new Entrance();
            entrance.setCredential(credential);
            entrance.setIp(ip);
            entrance.setUserAgent(userAgent);
            entranceRepository.save(entrance);
        }
    }
}
