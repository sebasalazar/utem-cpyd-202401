package cl.utem.ws.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "credentials")
public class Credential extends PkBaseEntity {

    @Column(name = "code", nullable = false, unique = true)
    private String code = null;

    @Column(name = "ws_tkn", nullable = false)
    private String token = null;

    @Column(name = "ws_key", nullable = false)
    private String key = null;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
